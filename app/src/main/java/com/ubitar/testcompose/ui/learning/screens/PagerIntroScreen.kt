package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

private data class PagerPage(
    val title: String,
    val description: String,
    val color: Color
)

private val demoPages = listOf(
    PagerPage("首页", "这一页通常放推荐内容或概览信息。", Color(0xFF2B6F63)),
    PagerPage("消息", "这一页可以展示最近通知、评论或聊天入口。", Color(0xFFD94F3D)),
    PagerPage("我的", "这一页常见的是个人资料、收藏和设置。", Color(0xFF26547C))
)

@Composable
fun PagerIntroScreen(onBack: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { demoPages.size })
    val scope = rememberCoroutineScope()

    LearningScreen(
        title = "Compose 版 ViewPager",
        summary = "在 Compose 里，类似 ViewPager 的功能通常直接用 HorizontalPager。它和 LazyRow 一样是声明式组件，但滚动单位是整页，而不是任意列表项。",
        onBack = onBack
    ) {
        section(
            title = "核心对应关系",
            body = "ViewPager2 的 adapter，在 Compose 里通常变成 HorizontalPager 的 page lambda；currentItem 对应 pagerState.currentPage；跳页则常用 animateScrollToPage()。"
        )
        block {
            CodeLikeLine("val pagerState = rememberPagerState(pageCount = { 3 })")
            CodeLikeLine("HorizontalPager(state = pagerState) { page -> ... }")
            CodeLikeLine("scope.launch { pagerState.animateScrollToPage(index) }")
        }
        section(
            title = "基础横向翻页",
            body = "下面这块就是最基础的 pager。左右滑动时，currentPage 会跟着变化。"
        )
        block {
            PagerCard(page = demoPages[pagerState.currentPage])
            TinyTag(
                text = "当前页 index = ${pagerState.currentPage}",
                color = demoPages[pagerState.currentPage].color
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) { page ->
                PagerCard(page = demoPages[page])
            }
        }
        section(
            title = "圆点指示器",
            body = "指示器本质上就是读取 pagerState.currentPage，然后自己画一排点。Compose 不需要额外的适配器回调。"
        )
        block {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                demoPages.forEachIndexed { index, page ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(if (index == pagerState.currentPage) 20.dp else 10.dp, 10.dp)
                            .background(
                                color = if (index == pagerState.currentPage) {
                                    page.color
                                } else {
                                    Color(0xFFD7D1C7)
                                },
                                shape = RoundedCornerShape(999.dp)
                            )
                    )
                }
            }
        }
        section(
            title = "TabRow 联动",
            body = "点击 Tab 时调用 animateScrollToPage()；滑动 pager 时，TabRow 直接读 currentPage 作为选中项，就实现了双向联动。"
        )
        block {
            PrimaryTabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = {
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(
                            selectedTabIndex = pagerState.currentPage,
                            matchContentSize = false
                        ),
                        width = Dp.Unspecified,
                    )
                }
            ) {
                demoPages.forEachIndexed { index, page ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(page.title) }
                    )
                }
            }
            ActionButton(
                text = "跳到最后一页",
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(demoPages.lastIndex)
                    }
                },
                color = Color(0xFF7A4E2D)
            )
        }
    }
}

@Composable
private fun PagerCard(page: PagerPage) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(page.color, RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TinyTag(text = page.title, color = Color(0x33000000))
            Text(
                text = page.title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}
