状态管理：使用mutableStateOf和remember管理当前图片的索引currentImageIndex，确保界面随状态变化自动重组。
布局结构：通过Column和Row实现垂直和水平布局，将 “图片展示区”、“信息文本区” 和 “按钮区” 分层排列。
图片展示：Image组件加载drawable中的图片资源，通过contentScale = ContentScale.Crop保证图片适配布局。
按钮功能：Previous和Next按钮通过修改currentImageIndex实现图片切换，利用取模运算%保证索引在合理范围内循环。
