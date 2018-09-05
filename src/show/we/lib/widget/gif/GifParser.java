package show.we.lib.widget.gif;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import java.io.InputStream;
import java.util.List;

/**
 * 解析Gif文件的类
 *
 * @author ll
 * @version 1.0.0
 */

public class GifParser {
    private static final int DEFAULT_BITMAP_DENSITY = 160;

    private int mWidth;
    private int mHeight;

    private boolean mGctFlag;
    private int mGctSize;

    private int[] mGct;
    private int[] mLct;
    private int[] mAct;

    private int mBgIndex;
    private int mBgColor;
    private int mLastBgColor;

    private boolean mInterlace;
    private int mLctSize;

    private int mIx, mIy, mIw, mIh;
    private int mLrx, mLry, mLrw, mLrh;
    private Bitmap mLastImage;

    private static final int BLOCK_SIZE = 256;
    private byte[] mBlock = new byte[BLOCK_SIZE];
    private int mBlockSize = 0;

    private int mDispose = 0;
    private int mLastDispose = 0;
    private boolean mTransparency = false;
    private int mDelay = 0;
    private int mTransIndex;

    private static final int MAX_STACK_SIZE = 4096;

    private short[] mPrefix;
    private byte[] mSuffix;
    private byte[] mPixelStack;
    private byte[] mPixels;

    /**
     * 解析Gift
     *
     * @param inputStream 输入流
     * @param frameList   输出的帧列表
     * @return 错误码
     */
    public int doParse(InputStream inputStream, final List<Frame> frameList) {
        try {
            if (inputStream == null) {
                throw new IllegalArgumentException("Src is NULL");
            }

            mGct = null;
            mLct = null;
            readHeader(inputStream);
            readContents(inputStream, frameList);
        } catch (Throwable e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    private void readHeader(InputStream inputStream) throws Exception {
        String id = "";
        for (int i = 0; i < 6; i++) {
            id += (char) read(inputStream);
        }

        if (!id.startsWith("GIF")) {
            throw new IllegalStateException("Error Format");
        }

        readLSD(inputStream);
        if (mGctFlag) {
            mGct = readColorTable(inputStream, mGctSize);
            mBgColor = mGct[mBgIndex];
        }
    }

    private static final int CODE_IMAGE = 0x2C;
    private static final int COE_21 = 0x21;
    private static final int CODE_FINISHED = 0x3b;
    private static final int CODE_NOTHING = 0x00;
    private static final int CODE_21_CONTROL_EXT = 0xf9;
    private static final int CODE_21_BLOCK = 0xff;
    private static final int CODE_1C = 0x1C;

    private void readContents(InputStream inputStream, final List<Frame> frameList) throws Exception {
        boolean finished = false;
        while (!finished) {
            int code = read(inputStream);
            switch (code) {
                case CODE_IMAGE:
                    readImage(inputStream, frameList);
                    break;
                case COE_21:
                    code = read(inputStream);
                    if (code == CODE_21_CONTROL_EXT) {
                        readGraphicControlExt(inputStream);
                    } else if (code == CODE_21_BLOCK) {
                        readBlock(inputStream);
                        String app = "";
                        for (int i = 0; i < 11; i++) {
                            app += (char) mBlock[i];
                        }
                        if (app.equals("NETSCAPE2.0")) {
                            readNetscapeExt(inputStream);
                        } else {
                            skip(inputStream);
                        }
                    } else {
                        skip(inputStream);
                    }
                    break;

                case CODE_FINISHED:
                    finished = true;
                    break;

                case CODE_NOTHING:
                    break;

                default:
                    throw new IllegalStateException("Illegal Code");
            }
        }
    }

    private void readGraphicControlExt(InputStream inputStream) throws Exception {
        read(inputStream);
        int packed = read(inputStream);
        mDispose = (packed & CODE_1C) >> 2;
        if (mDispose == 0) {
            mDispose = 1;
        }
        mTransparency = (packed & 1) != 0;
        mDelay = readShort(inputStream) * 10;
        mTransIndex = read(inputStream);
        read(inputStream);
    }

    private int readBlock(InputStream inputStream) throws Exception {
        mBlockSize = read(inputStream);
        int n = 0;
        if (mBlockSize > 0) {
            try {
                int count = 0;
                while (n < mBlockSize) {
                    count = inputStream.read(mBlock, n, mBlockSize - n);
                    if (count == -1) {
                        break;
                    }
                    n += count;
                }
            } catch (Exception e) {
                throw new IllegalStateException("Error Format");
            }
        }
        return n;
    }

    private int read(InputStream inputStream) throws Exception {
        return inputStream.read();
    }

    private int readShort(InputStream inputStream) throws Exception {
        return read(inputStream) | (read(inputStream) << 8);
    }

    private void readLSD(InputStream inputStream) throws Exception {
        mWidth = readShort(inputStream);
        mHeight = readShort(inputStream);
        int packed = read(inputStream);
        mGctFlag = (packed & 0x80) != 0;
        mGctSize = 2 << (packed & 7);
        mBgIndex = read(inputStream);
        read(inputStream);
    }

    private void readNetscapeExt(InputStream inputStream) throws Exception {
        do {
            readBlock(inputStream);
//            if (mBlock[0] == 1) {
//                int b1 = ((int) mBlock[1]) & 0xff;
//                int b2 = ((int) mBlock[2]) & 0xff;
//            }
        } while (mBlockSize > 0);
    }

    private void decodeImage(InputStream inputStream, final List<Frame> frameList) throws Exception {
        int mNullCode = -1;
        int nPix = mIw * mIh;
        int available;
        int clear;
        int codeMask;
        int codeSize;
        int endOfInformation;
        int inCode;
        int oldCode;
        int bits = 0;
        int code;
        int count = 0;
        int i;
        int datum = 0;
        int dataSize;
        int first = 0;
        int top = 0;
        int bi = 0;
        int pi = 0;

        if ((mPixels == null) || (mPixels.length < nPix)) {
            mPixels = new byte[nPix]; // allocate new pixel array
        }
        if (mPrefix == null) {
            mPrefix = new short[MAX_STACK_SIZE];
        }
        if (mSuffix == null) {
            mSuffix = new byte[MAX_STACK_SIZE];
        }
        if (mPixelStack == null) {
            mPixelStack = new byte[MAX_STACK_SIZE + 1];
        }

        dataSize = read(inputStream);
        clear = 1 << dataSize;
        endOfInformation = clear + 1;
        available = clear + 2;
        oldCode = mNullCode;
        codeSize = dataSize + 1;
        codeMask = (1 << codeSize) - 1;
        for (code = 0; code < clear; code++) {
            mPrefix[code] = 0;
            mSuffix[code] = (byte) code;
        }

        // Decode GIF pixel stream.
        //datum = bits = count = first = top = pi = bi = 0;
        i = 0;
        while (i < nPix) {
            if (top == 0) {
                if (bits < codeSize) {
                    // Load bytes until there are enough bits for a code.
                    if (count == 0) {
                        // Read a new data mBlock.
                        count = readBlock(inputStream);
                        if (count <= 0) {
                            break;
                        }
                        bi = 0;
                    }
                    datum += (((int) mBlock[bi]) & 0xff) << bits;
                    bits += 8;
                    bi++;
                    count--;
                    continue;
                }
                // Get the next code.
                code = datum & codeMask;
                datum >>= codeSize;
                bits -= codeSize;

                // Interpret the code
                if ((code > available) || (code == endOfInformation)) {
                    break;
                }
                if (code == clear) {
                    // Reset decoder.
                    codeSize = dataSize + 1;
                    codeMask = (1 << codeSize) - 1;
                    available = clear + 2;
                    oldCode = mNullCode;
                    continue;
                }
                if (oldCode == mNullCode) {
                    mPixelStack[top++] = mSuffix[code];
                    oldCode = code;
                    first = code;
                    continue;
                }
                inCode = code;
                if (code == available) {
                    mPixelStack[top++] = (byte) first;
                    code = oldCode;
                }
                while (code > clear) {
                    mPixelStack[top++] = mSuffix[code];
                    code = mPrefix[code];
                }
                first = ((int) mSuffix[code]) & 0xff;
                // Add a new string to the string table,
                if (available >= MAX_STACK_SIZE) {
                    break;
                }
                mPixelStack[top++] = (byte) first;
                mPrefix[available] = (short) oldCode;
                mSuffix[available] = (byte) first;
                available++;
                if (((available & codeMask) == 0)
                        && (available < MAX_STACK_SIZE)) {
                    codeSize++;
                    codeMask += available;
                }
                oldCode = inCode;
            }

            top--;
            mPixels[pi++] = mPixelStack[top];
            i++;
        }
        for (i = pi; i < nPix; i++) {
            mPixels[i] = 0;
        }

        skip(inputStream);

        int[] dest = new int[mWidth * mHeight];

        if (mLastDispose > 0) {
            if (mLastDispose == 3) {
                int n = frameList.size() - 1;
                if (n > 0) {
                    mLastImage = frameList.get(n - 1).getBitmap();
                } else {
                    mLastImage = null;
                }
            }
            if (mLastImage != null) {
                mLastImage.getPixels(dest, 0, mWidth, 0, 0, mWidth, mHeight);
                if (mLastDispose == 2) {
                    int c = 0;
                    if (!mTransparency) {
                        c = mLastBgColor;
                    }
                    for (i = 0; i < mLrh; i++) {
                        int n1 = (mLry + i) * mWidth + mLrx;
                        int n2 = n1 + mLrw;
                        for (int k = n1; k < n2; k++) {
                            dest[k] = c;
                        }
                    }
                }
            }
        }

        int pass = 1;
        int inc = 8;
        int iline = 0;
        for (i = 0; i < mIh; i++) {
            int line = i;
            if (mInterlace) {
                if (iline >= mIh) {
                    pass++;
                    switch (pass) {
                        case 2:
                            iline = 4;
                            break;
                        case 3:
                            iline = 2;
                            inc = 4;
                            break;
                        case 4:
                            iline = 1;
                            inc = 2;
                            break;
                        default:
                            break;
                    }
                }
                line = iline;
                iline += inc;
            }
            line += mIy;
            if (line < mHeight) {
                int k = line * mWidth;
                int dx = k + mIx;
                int dlim = dx + mIw;
                if ((k + mWidth) < dlim) {
                    dlim = k + mWidth;
                }
                int sx = i * mIw;
                while (dx < dlim) {
                    int index = ((int) mPixels[sx++]) & 0xff;
                    int c = mAct[index];
                    if (c != 0) {
                        dest[dx] = c;
                    }
                    dx++;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(dest, mWidth, mHeight, Config.ARGB_8888);
        bitmap.setDensity(DEFAULT_BITMAP_DENSITY);
        frameList.add(new Frame(bitmap, mDelay));
    }

    private int[] readColorTable(InputStream inputStream, int colorCount) throws Exception {
        int size = 3 * colorCount;
        byte[] buffer = new byte[size];
        int n = inputStream.read(buffer);
        if (n < size) {
            throw new IllegalStateException("Error Format");
        } else {
            int[] tab = new int[256];
            int i = 0;
            int j = 0;
            while (i < colorCount) {
                int r = ((int) buffer[j++]) & 0xff;
                int g = ((int) buffer[j++]) & 0xff;
                int b = ((int) buffer[j++]) & 0xff;
                tab[i++] = 0xff000000 | (r << 16) | (g << 8) | b;
            }
            return tab;
        }
    }

    private void readImage(InputStream inputStream, final List<Frame> frameList) throws Exception {
        mIx = readShort(inputStream);
        mIy = readShort(inputStream);
        mIw = readShort(inputStream);
        mIh = readShort(inputStream);
        int packed = read(inputStream);
        boolean lctFlag = (packed & 0x80) != 0;
        mInterlace = (packed & 0x40) != 0;
        mLctSize = 2 << (packed & 7);
        if (lctFlag) {
            mLct = readColorTable(inputStream, mLctSize); // read table
            mAct = mLct;
        } else {
            mAct = mGct;
            if (mBgIndex == mTransIndex) {
                mBgColor = 0;
            }
        }

        if (mAct == null) {
            throw new IllegalStateException("Act is NULL");
        }

        int save = 0;
        if (mTransparency) {
            save = mAct[mTransIndex];
            mAct[mTransIndex] = 0; // set transparent color if specified
        }

        decodeImage(inputStream, frameList);
        if (mTransparency) {
            mAct[mTransIndex] = save;
        }
        resetFrame(frameList);
    }

    private void resetFrame(final List<Frame> frameList) {
        mLastDispose = mDispose;
        mLrx = mIx;
        mLry = mIy;
        mLrw = mIw;
        mLrh = mIh;

        mLastImage = (frameList.size() > 0) ? frameList.get(frameList.size() - 1).getBitmap() : null;

        mLastBgColor = mBgColor;
        mDispose = 0;
        mTransparency = false;
        mDelay = 0;
        mLct = null;
    }

    private void skip(InputStream inputStream) throws Exception {
        do {
            readBlock(inputStream);
        } while (mBlockSize > 0);
    }
}
