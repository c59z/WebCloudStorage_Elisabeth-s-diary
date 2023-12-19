export default {
    data() {
        return {
            isSmallScreen: false,
        };
    },
    methods: {
        handleResize() {
            // 根据窗口宽度设置isSmallScreen的值
            this.isSmallScreen = window.innerWidth < 992; // 例如，小于768px宽度的窗口被认为是小屏幕
            console.log("调用全局方法-检测到窗口变换")
        },
    },
    mounted() {
        // 监听窗口大小变化
        window.addEventListener('resize', this.handleResize);
        // 初始化时检测窗口大小
        this.handleResize();
    },
    beforeDestroy() {
        // 在组件销毁前移除事件监听器
        window.removeEventListener('resize', this.handleResize);
    }
};
