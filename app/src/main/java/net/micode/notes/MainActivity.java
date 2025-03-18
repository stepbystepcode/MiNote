package net.micode.notes;

/**
 * MainActivity 类是应用程序的主入口活动
 * 
 * 功能：
 * - 作为应用程序的启动入口
 * - 负责初始化应用环境和资源
 * - 处理应用程序的启动逻辑
 * - 引导用户进入笔记列表界面
 * - 管理应用程序的生命周期
 * 
 * 改进点：
 * 1. 应增加启动画面和品牌展示
 * 2. 可添加用户引导和教程功能
 * 3. 应优化启动性能和加载时间
 * 4. 缺少用户登录和账户管理功能
 * 
 * 不足之处：
 * 1. 启动逻辑过于简单，缺少必要的初始化检查
 * 2. 未处理可能的启动异常情况
 * 3. 缺少适当的版本兼容性检查
 * 4. 未提供多用户支持和权限管理
 */

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}