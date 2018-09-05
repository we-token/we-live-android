package show.we.lib.utils;

import android.graphics.Paint;
import android.widget.*;

import java.lang.reflect.Field;

/**
 * Created by CG on 14-4-2.
 * @author ll
 * @version 3.7.0
 */
public class TimePickerUtils {

    private static final int SELECT_COLOR = 0xff9933cc;
    private static final int NORMAL_COLOR = 0xff777777;

    /**
     * setTimePickerUi
     *
     * @param timePicker timePicker
     */
    public static void setTimePickerUi(TimePicker timePicker) {
        setTimePickerUi(timePicker, SELECT_COLOR, NORMAL_COLOR);
    }

    /**
     * setTimePickerUi
     * @param timePicker timePicker
     * @param selectColor selectColor
     * @param normalColor normalColor
     */
    public static void setTimePickerUi(TimePicker timePicker, int selectColor, int normalColor) {
        TimePickerUi timePickerUi = new TimePickerUi(timePicker);
        if (timePickerUi.getHourSpinner() != null) {
            setNumberPickerUi(timePickerUi.getHourSpinner(), selectColor, normalColor);
        }
        if (timePickerUi.getMinuteSpinner() != null) {
            setNumberPickerUi(timePickerUi.getMinuteSpinner(), selectColor, normalColor);
        }
        if (timePickerUi.getAmPmSpinner() != null) {
            setNumberPickerUi(timePickerUi.getAmPmSpinner(), selectColor, normalColor);
        }
        if (timePickerUi.getDivider() != null) {
            timePickerUi.getDivider().setTextColor(normalColor);
        }
    }

    private static void setNumberPickerUi(NumberPicker numberPicker, int selectColor, int normalColor) {
        NumberPickerUi numberPickerUi = new NumberPickerUi(numberPicker);
        if (numberPickerUi.getInputText() != null) {
            numberPickerUi.getInputText().setTextColor(selectColor);
        }
        if (numberPickerUi.getSelectorWheelPaint() != null) {
            numberPickerUi.getSelectorWheelPaint().setColor(normalColor);
        }
    }

    private static final class TimePickerUi {

        private TimePicker mTimePicker;

        private NumberPicker mHourSpinner2;

        private NumberPicker mMinuteSpinner2;

        private NumberPicker mAmPmSpinner2;

        private EditText mHourSpinnerInput2;

        private EditText mMinuteSpinnerInput2;

        private EditText mAmPmSpinnerInput2;

        private Button mAmPmButton2;

        private TextView mDivider2;

        /**
         * TimePickerUi
         *
         * @param timePicker timePicker
         */
        private TimePickerUi(TimePicker timePicker) {
            mTimePicker = timePicker;
            mHourSpinner2 = (NumberPicker) getField("mHourSpinner");
            mMinuteSpinner2 = (NumberPicker) getField("mMinuteSpinner");
            mAmPmSpinner2 = (NumberPicker) getField("mAmPmSpinner");
            mHourSpinnerInput2 = (EditText) getField("mHourSpinnerInput");
            mMinuteSpinnerInput2 = (EditText) getField("mMinuteSpinnerInput");
            mAmPmSpinnerInput2 = (EditText) getField("mAmPmSpinnerInput");
            mAmPmButton2 = (Button) getField("mAmPmButton");
            mDivider2 = (TextView) getField("mDivider");
        }

        private Object getField(String fieldName) {
            try {
                Field field = TimePicker.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(mTimePicker);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * getHourSpinner
         *
         * @return HourSpinner
         */
        private NumberPicker getHourSpinner() {
            return mHourSpinner2;
        }

        /**
         * getMinuteSpinner
         *
         * @return MinuteSpinner
         */
        private NumberPicker getMinuteSpinner() {
            return mMinuteSpinner2;
        }

        /**
         * getAmPmSpinner
         *
         * @return AmPmSpinner
         */
        private NumberPicker getAmPmSpinner() {
            return mAmPmSpinner2;
        }

        /**
         * getHourSpinnerInput
         *
         * @return HourSpinnerInput
         */
        private EditText getHourSpinnerInput() {
            return mHourSpinnerInput2;
        }

        /**
         * getMinuteSpinnerInput
         *
         * @return MinuteSpinnerInput
         */
        private EditText getMinuteSpinnerInput() {
            return mMinuteSpinnerInput2;
        }

        /**
         * getAmPmSpinnerInput
         *
         * @return AmPmSpinnerInput
         */
        private EditText getAmPmSpinnerInput() {
            return mAmPmSpinnerInput2;
        }

        /**
         * getAmPmButton
         *
         * @return AmPmButton
         */
        private Button getAmPmButton() {
            return mAmPmButton2;
        }

        /**
         * getDivider
         *
         * @return Divider
         */
        private TextView getDivider() {
            return mDivider2;
        }
    }

    private static final class NumberPickerUi {

        private NumberPicker mNumberPicker;

        private ImageButton mIncrementButton2;
        private ImageButton mDecrementButton2;
        private EditText mInputText2;

        private Paint mSelectorWheelPaint2;

        /**
         * NumberPickerUi
         *
         * @param numberPicker numberPicker
         */
        private NumberPickerUi(NumberPicker numberPicker) {
            mNumberPicker = numberPicker;
            mIncrementButton2 = (ImageButton) getField("mIncrementButton");
            mDecrementButton2 = (ImageButton) getField("mDecrementButton");
            mInputText2 = (EditText) getField("mInputText");

            mSelectorWheelPaint2 = (Paint) getField("mSelectorWheelPaint");
        }

        private Object getField(String fieldName) {
            try {
                Field field = NumberPicker.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(mNumberPicker);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * getIncrementButton
         *
         * @return IncrementButton
         */
        private ImageButton getIncrementButton() {
            return mIncrementButton2;
        }

        /**
         * getDecrementButton
         *
         * @return DecrementButton
         */
        private ImageButton getDecrementButton() {
            return mDecrementButton2;
        }

        /**
         * getInputText
         *
         * @return InputText
         */
        private EditText getInputText() {
            return mInputText2;
        }

        /**
         * getSelectorWheelPaint
         *
         * @return SelectorWheelPaint
         */
        private Paint getSelectorWheelPaint() {
            return mSelectorWheelPaint2;
        }
    }
}
