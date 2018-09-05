package show.we.lib.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import show.we.lib.R;
import show.we.lib.widget.wheelview.OnWheelChangedListener;
import show.we.lib.widget.wheelview.OnWheelScrollListener;
import show.we.lib.widget.wheelview.WheelView;
import show.we.lib.widget.wheelview.adapters.ArrayWheelAdapter;

/**
 * @author ll
 * @version 1.0.0
 */
public class AddressSelectDialog extends BaseDialog {

    /**
     * 地址选择回调接口
     */
    public interface OnAddressSelectedListener {
        /**
         * 选择某个地址
         *
         * @param province 省份名
         * @param city     城市名
         */
        public void onAddressSelectedEvent(String province, String city);
    }

    private static final String[] PROVINCES = new String[]{"北京", "天津", "重庆", "上海", "河北"
            , "山西", "内蒙古", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建", "江西"
            , "山东", "河南", "湖北", "湖南", "广东", "广西", "海南", "四川", "贵州", "云南", "西藏"
            , "陕西", "甘肃", "青海", "宁夏", "新疆", "香港", "澳门", "台湾"};

    private static final String[][] CITIES = new String[][]{
            new String[]{"海淀", "朝阳", "东城", "崇文", "丰台", "昌平", "西城", "门头沟", "宣武", "石景山", "房山", "通州", "顺义", "大兴", "怀柔", "平谷", "密云", "延庆"},
            new String[]{"和平", "红桥", "西青", "河西", "河东", "溏沽", "北辰", "汉沽", "大港", "河北", "东丽", "南开", "津南", "宁河", "武清", "静海", "宝坻", "蓟县"},
            new String[]{"渝中", "大渡口", "江北", "沙坪坝", "九龙坡", "南岸", "北碚", "綦江", "双桥", "渝北", "巴南", "万州", "涪陵", "黔江", "长寿", "江津", "合川", "永川", "南川", "綦江县", "潼南县", "铜梁县", "大足县", "荣昌县", "璧山县", "梁平县", "城口县", "丰都县", "垫江县", "武隆县", "忠县", "开县", "云阳县", "奉节县", "巫山县", "巫溪县"},
            new String[]{"黄浦", "长宁", "杨浦", "南市", "闸北", "普陀", "卢湾", "静安", "宝山", "徐汇", "虹口", "闵行", "浦东新区", "嘉定", "金山", "松江", "青浦", "南汇", "奉贤", "崇明"},
            new String[]{"石家庄", "唐山", "秦皇岛", "邯郸", "邢台", "保定", "张家口", "承德", "沧州", "廊坊", "衡水", "正定", "辛集", "滦县", "玉田", "遵化", "清河", "涿州", "曲阳", "吴桥", "固安", "三河", "冀州"},
            new String[]{"太原", "阳泉", "介休", "大同", "长治", "晋城", "晋中", "临汾", "离石", "忻州", "运城", "朔州", "盂县", "阳曲", "广灵", "沁源", "沁水", "寿阳", "平遥", "孝义", "汾阳", "文水", "宁武", "河曲", "万荣", "怀仁"},
            new String[]{"呼和浩特", "乌海", "包头", "赤峰", "临河", "海拉尔", "集宁", "锡林浩特", "乌兰浩特", "东胜", "通辽", "霍林郭勒", "阿拉善盟", "额济纳旗"},
            new String[]{"沈阳", "鞍山", "本溪", "朝阳", "丹东", "抚顺", "阜新", "葫芦岛", "锦州", "辽阳", "大连", "盘锦", "铁岭", "营口", "新民", "辽中", "海城", "瓦房店", "庄河", "凤城", "绥中", "凌海", "黑山"},
            new String[]{"长春", "吉林", "四平", "辽源", "通化", "白山", "松原", "白城", "延吉", "珲春", "梅河山", "榆树", "桦甸", "舒兰", "公主岭", "双辽", "梨树", "靖宇", "通榆", "大安", "敦化", "安图"},
            new String[]{"哈尔滨", "大庆", "大兴安岭", "鹤岗", "黑河", "佳木斯", "牡丹江", "七台河", "齐齐哈尔", "双鸭山", "伊春", "绥化", "安达", "肇东", "海伦", "漠河", "嫩江", "抚远", "汤原", "绥芬河", "富裕", "依安", "宝清", "友谊", "嘉荫"},
            new String[]{"南京", "常州", "无锡", "张家港", "昆山", "太仓", "淮安", "连云港", "徐州", "宿迁", "盐城", "扬州", "苏州", "泰州", "南通", "镇江", "江宁", "武进", "淮阴", "洪泽", "盱眙", "沛县", "沭阳", "滨海", "江都", "泰兴", "靖江", "启东", "如皋", "扬中", "句容", "宜兴", "江阴", "吴江"},
            new String[]{"杭州", "宁波", "温州", "绍兴", "嘉兴", "湖州", "金华", "衢州", "舟山", "台州", "丽水", "建德", "富阳", "余杭", "临安", "桐芦", "淳安", "萧山", "余姚", "慈溪", "瑞安", "乐清", "永嘉", "平阳", "海宁", "桐乡", "嘉善", "安吉", "诸暨", "上虞", "嵊州", "新昌", "义乌", "东阳", "天台"},
            new String[]{"合肥", "淮南", "淮北", "芜湖", "铜陵", "蚌埠", "马鞍山", "安庆", "黄山", "滁州", "阜阳", "毫州", "宿州", "巢湖", "六安", "宣城", "贵池", "濉溪", "太湖", "凤阳", "颍上", "太和", "蒙城", "泗县", "和县", "寿县", "泾县"},
            new String[]{"福州", "厦门", "泉州", "三明", "莆田", "漳州", "南平", "龙岩", "宁德", "福安", "晋江", "长乐", "永安", "沙县", "仙游", "石狮", "金门", "诏安", "云霄", "武夷山", "寿宁", "古田"},
            new String[]{"南昌", "景德镇", "萍乡", "新余", "九江", "鹰潭", "赣州", "瑞金", "宜春", "上饶", "临川", "吉安", "上栗", "芦溪", "莲花", "武宁", "瑞昌", "赣县", "婺源", "玉山", "广昌", "井冈山", "吉水", "万安"},
            new String[]{"济南", "淄博", "枣庄", "东营", "潍坊", "烟台", "威海", "济宁", "青岛", "泰安", "日照", "莱芜", "德州", "临沂", "聊城", "滨州", "菏泽", "兖州", "邹城", "微山泗水", "梁山", "曲阜", "沂水", "临沭", "阳谷", "郓城"},
            new String[]{"郑州", "开封", "洛阳", "平顶山", "焦作", "鹤壁", "新乡", "安阳", "濮阳", "许昌", "漯河", "三门峡", "南阳", "商丘", "信阳", "济源", "周口", "驻马店", "巩义", "登封荥阳", "兰考", "郏县", "沁阳", "新野", "固始", "正阳"},
            new String[]{"武汉", "黄石", "襄樊", "十堰", "荆州", "荆门", "鄂州", "孝感", "咸宁", "黄冈", "宜昌", "随州", "神农架", "仙桃", "天门", "潜江", "恩施", "大治", "枣阳", "襄阳", "丹江口", "郧县", "竹山", "竹溪", "公安", "江陵", "洪湖", "当阳", "枝江", "京山", "广水", "赤壁", "蕲春", "宣恩", "咸丰"},
            new String[]{"长沙", "株洲", "湘潭", "衡阳", "邵阳", "岳阳", "常德", "张家界", "彬州", "益阳", "永州", "怀化", "娄底", "冷水江", "湘西", "宁乡", "浏阳", "醴陵", "攸县", "湘乡", "韶山", "耒阳", "常宁", "祁东", "洞口", "绥宁", "华容", "桃源", "嘉禾", "沅江", "凤凰"},
            new String[]{"广州", "深圳", "汕头", "东莞", "佛山", "韶关", "河源", "梅州", "惠州", "汕尾", "中山", "阳江", "湛江", "茂名", "珠海", "肇庆", "云浮", "清远", "潮州", "揭阳", "潮阳", "顺德", "花都", "番禺", "从化", "增城", "澄海", "曲江", "龙川", "紫金", "梅县", "惠阳", "开平", "三水", "高州", "广宁", "罗定", "普宁"},
            new String[]{"桂林", "梧州", "北海", "防城港", "钦州", "贵港", "玉林", "南宁", "柳州", "贺州", "百色", "河池", "临桂", "灵川", "阳朔", "藤县", "灵山", "容县", "邕宁", "合山", "昭平"},
            new String[]{"海口", "三亚", "通什", "琼海", "儋州", "琼山", "文昌", "万宁", "东方", "澄迈", "定安"},
            new String[]{"成都", "自贡", "攀枝花", "泸州", "德阳", "绵阳", "广元", "遂宁", "内江", "乐山", "南充", "宜宾", "广安", "达州", "华蓥", "资阳", "巴中", "雅安", "眉山", "阿坝", "甘孜", "西昌"},
            new String[]{"贵阳", "六盘水", "遵义", "铜仁", "毕节", "安顺", "清镇", "赤水", "仁怀", "绥阳", "桐梓"},
            new String[]{"昆明", "黔西南", "凯里", "黔南", "曲靖", "玉溪", "昭通", "思茅", "临沧", "保山", "丽江", "文山", "红河", "西双版纳", "楚雄", "大理", "德宏", "怒江", "迪庆"},
            new String[]{"拉萨","昌都","日喀则","山南地区","那曲地区","阿里地区","林芝地区"},
            new String[]{"西安", "铜川", "宝鸡", "咸阳", "渭南", "延安", "汉中", "榆林", "商洛", "安康"},
            new String[]{"兰州", "金昌", "白银", "天水", "嘉峪关", "定西", "平凉", "庆阳", "陇南", "武威", "张掖", "酒泉", "甘南", "临夏"},
            new String[]{"西宁","海东市","海北","黄南","海南","果洛","玉树","海西"},
            new String[]{"银川", "石嘴山", "吴忠", "青铜峡", "固原"},
            new String[]{"乌鲁木齐", "克拉玛依", "石河子", "吐鲁番", "哈密", "和田", "阿克苏", "喀什", "阿图什", "库尔勒", "吉昌", "博乐", "伊宁", "阿勒泰", "塔城", "乌苏"},
            new String[]{"中西", "湾仔", "东", "南", "九龙", "油尖旺", "深水埗", "九龙城", "黄大仙", "观塘", "新界", "北", "大埔", "沙田", "西贡", "荃湾", "屯门", "元朗", "葵青", "离岛"},
            new String[]{"花地玛堂", "圣安多尼堂", "大堂", "望德堂", "风顺堂", "澳门离岛", "嘉模堂", "圣方济各堂", "无堂划分域", "路氹城"},
            new String[]{"基隆", "新竹", "台中", "嘉义", "台南", "台北", "宜兰", "桃园", "新竹", "苗栗", "台中"
                    , "彰化", "南投", "云林", "嘉义", "台南", "高雄", "屏东", "台东", "花莲", "澎湖", "金门", "连江"},
    };

    private boolean mScrolling = false;

    private WheelView mProvinceView;
    private WheelView mCityView;

    /**
     * constructor
     *
     * @param context  context
     * @param listener listener
     */
    public AddressSelectDialog(Context context, final OnAddressSelectedListener listener) {
        super(context, R.layout.layout_address_select);
        final int visibleItemCount = 5;
        mCityView = (WheelView) findViewById(R.id.id_city);
        mCityView.setVisibleItems(visibleItemCount);

        mProvinceView = (WheelView) findViewById(R.id.id_province);
        mProvinceView.setVisibleItems(3);
        setAdapter(mProvinceView, PROVINCES, Gravity.RIGHT);
        mProvinceView.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!mScrolling) {
                    setAdapter(mCityView, CITIES[mProvinceView.getCurrentItem()], Gravity.LEFT);
                }
            }
        });
        mProvinceView.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                mScrolling = true;
            }

            public void onScrollingFinished(WheelView wheel) {
                mScrolling = false;
                setAdapter(mCityView, CITIES[mProvinceView.getCurrentItem()], Gravity.LEFT);
            }
        });
        mProvinceView.setCurrentItem(3);

        findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(mProvinceView.isScrolling ()||mCityView.isScrolling()){

            	}else{
            		findViewById(R.id.sure).setClickable(true);
            		ArrayWheelAdapter<String> adapter = (ArrayWheelAdapter<String>) mProvinceView.getViewAdapter();
                    String province = adapter.getItemText(mProvinceView.getCurrentItem()).toString();
                    adapter = (ArrayWheelAdapter<String>) mCityView.getViewAdapter();
                    String city = adapter.getItemText(mCityView.getCurrentItem()).toString();
                    listener.onAddressSelectedEvent(province, city);
                    cancel();
            	}        
            }
        });
    }

    private void setAdapter(WheelView city, String[] dataArray, int gravity) {
        final int textSize = 18;
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(getContext(), dataArray);
        adapter.setTextGravity(gravity);
        adapter.setTextSize(textSize);
        city.setViewAdapter(adapter);
        city.setCurrentItem(dataArray.length / 2);
    }
}
