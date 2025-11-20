package com.example.myapplication03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication03.ui.theme.MyApplication03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication03Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 将我们的图片画廊屏幕应用到 Scaffold 中
                    // 使用 innerPadding 来避免内容被系统栏覆盖
                    ImageGalleryScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// 我们的主图片画廊界面
@Composable
fun ImageGalleryScreen(modifier: Modifier = Modifier) {
    // 1. 状态管理：记住当前显示的图片索引
    var currentImageIndex by remember { mutableStateOf(0) }

    // 2. 准备数据：图片资源ID列表
    // 请确保你已经将这些图片文件放入 drawable 目录
    val imageResources = listOf(
        R.drawable.mountain1,  // 替换成你的图片文件名
        R.drawable.mountain2,
        R.drawable.mountain3
    )

    // 3. 准备数据：与图片对应的信息
    val imageInfos = listOf(
        ImageInfo("Mountain Peaks at Dawn", "Noah Wilson", "Noah Wilson (2023)"),
        ImageInfo("Alpine Sunset", "Emma Davis", "Emma Davis (2022)"),
        ImageInfo("Snowy Peaks", "Liam Smith", "Liam Smith (2021)")
    )

    // 4. 布局：使用 Column 垂直排列所有元素
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), // 整体内边距
        horizontalAlignment = Alignment.CenterHorizontally, // 水平居中
        verticalArrangement = Arrangement.Top // 垂直方向从顶部开始排列
    ) {
        // 图片标题
        Text(
            text = "Beautiful Landscapes",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 图片展示区域 (占满可用空间的主要部分)
        Image(
            painter = painterResource(id = imageResources[currentImageIndex]),
            contentDescription = imageInfos[currentImageIndex].title, // 为无障碍功能提供描述
            contentScale = ContentScale.Crop, // 图片缩放模式
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // 让图片区域占据 Column 中剩余的大部分空间
                .aspectRatio(16f / 9f) // 设置一个宽高比，使图片不会被拉伸变形
                .padding(bottom = 16.dp)
        )

        // 图片信息区域
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = imageInfos[currentImageIndex].title,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "by ${imageInfos[currentImageIndex].author}",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = imageInfos[currentImageIndex].details,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        // 按钮区域
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly // 按钮均匀分布
        ) {
            Button(
                onClick = {
                    // 上一张：索引减1，如果小于0则循环到最后一张
                    currentImageIndex = (currentImageIndex - 1 + imageResources.size) % imageResources.size
                },
                // 当只有一张图片时，禁用按钮
                enabled = imageResources.size > 1
            ) {
                Text(text = "Previous")
            }

            Button(
                onClick = {
                    // 下一张：索引加1，如果超出范围则循环到第一张
                    currentImageIndex = (currentImageIndex + 1) % imageResources.size
                },
                enabled = imageResources.size > 1
            ) {
                Text(text = "Next")
            }
        }
    }
}

// 数据类：用于存储每张图片的相关信息
data class ImageInfo(
    val title: String,
    val author: String,
    val details: String
)

// 预览函数
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun ImageGalleryPreview() {
    MyApplication03Theme {
        Scaffold { innerPadding ->
            ImageGalleryScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}