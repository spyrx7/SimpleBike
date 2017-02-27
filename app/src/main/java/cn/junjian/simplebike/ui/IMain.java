package cn.junjian.simplebike.ui;

/**
 * Created by junjianliu
 * on 17/2/14
 * email:spyhanfeng@qq.com
 */

public interface IMain {

    // 初始化
    void init();

    // 搜索
    void sreach();
    // 个人信息
    void userinfo();
    // 初始化地图（获取定位，加载附近车辆）
    void initMap();
    // 刷新
    void refresh();
    // 客服帮助
    void help();
    // 扫一扫
    void richScan();

}
