package com.example.grabit.ui.flow.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.grabit.R
import com.example.grabit.ui.components.PagerIndicator
import com.example.grabit.ui.components.PrimaryButton
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme
import kotlinx.coroutines.launch

@Composable
fun IntroScreen() {
    val viewModel = hiltViewModel<IntroViewModel>()
    val pagerState = rememberPagerState { introList.size }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            if (pagerState.currentPage == 2) {
                ""
            } else {
                stringResource(R.string.btn_skip)
            },
            style = AppTheme.appTypography.button.copy(
                color = colorScheme.primary
            ),
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            IntroItem(introList[page])
        }

        PagerIndicator(
            pagerState = pagerState,
            count = introList.size,
            activeColor = colorScheme.primary,
            inactiveColor = colorScheme.containerHigh,
            activeIndicatorWidth = 20.dp,
            spacing = 6.dp
        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = if (pagerState.currentPage == 2) {
                stringResource(id = R.string.get_started)
            } else {
                stringResource(id = R.string.common_btn_next)
            },
            onClick = {
                if (pagerState.currentPage == 2) {
                    viewModel.completedIntro()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun IntroItem(intro: Intro) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = intro.title),
            textAlign = TextAlign.Center,
            style = AppTheme.appTypography.header1.copy(color = colorScheme.textPrimary)
        )
        Spacer(modifier = Modifier.weight(0.5f))
        Image(
            painter = painterResource(id = intro.image),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit,
            contentDescription = "content image"
        )
        Spacer(modifier = Modifier.weight(0.5f))
        Text(
            text = stringResource(id = intro.content),
            textAlign = TextAlign.Center,
            style = AppTheme.appTypography.subTitle1.copy(color = colorScheme.textSecondary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.weight(0.5f))
    }
}

data class Intro(
    val title: Int,
    val content: Int,
    val image: Int,
)

private val introList = listOf(
    Intro(
        title = R.string.intro_title_one,
        content = R.string.intro_content_one,
        image = R.drawable.iv_intro_one
    ),
    Intro(
        title = R.string.intro_title_two,
        content = R.string.intro_content_two,
        image = R.drawable.iv_intro_two
    ),
    Intro(
        title = R.string.intro_title_three,
        content = R.string.intro_content_three,
        image = R.drawable.iv_intro_three
    )
)
