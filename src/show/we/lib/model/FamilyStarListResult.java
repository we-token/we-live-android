package show.we.lib.model;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by CG on 13-11-18.
 *
 * @author ll
 * @version 3.1.0
 */
public class FamilyStarListResult extends BaseListResult<FamilyStarData> {

    public void sort() {
        List<FamilyStarData> dataList = getDataList();
        if (dataList == null || dataList.size() < 2) {
            return;
        }
        Collections.sort(dataList, new Comparator<FamilyStarData>() {
            @Override
            public int compare(FamilyStarData lhs, FamilyStarData rhs) {
                if (lhs.getStar().isLive() == rhs.getStar().isLive()) {
                    return Collator.getInstance(Locale.CHINESE).compare(lhs.getStar().getNickName(), rhs.getStar().getNickName());
                } else {
                    return lhs.getStar().isLive() ? -1 : 1;
                }
            }
        });
    }
}
