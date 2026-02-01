package com.example.paging.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.paging.ui.theme.DarkCardBackground
import com.example.paging.ui.theme.PagingTheme

@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000
) {
    val shimmerColors = listOf(
        Color.DarkGray.copy(alpha = 0.3f),
        Color.DarkGray.copy(alpha = 0.5f),
        Color.DarkGray.copy(alpha = 0.7f),
        Color.DarkGray.copy(alpha = 0.5f),
        Color.DarkGray.copy(alpha = 0.3f)
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnimation - widthOfShadowBrush, y = 0f),
        end = Offset(x = translateAnimation, y = angleOfAxisY)
    )

    Box(modifier = modifier.background(brush))
}

@Composable
fun ShimmerUserCard(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkCardBackground)
            .padding(16.dp)
    ) {
        Column {
            // Header row with avatar and name
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Avatar shimmer
                ShimmerEffect(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    // Name shimmer
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Badges shimmer
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ShimmerEffect(
                            modifier = Modifier
                                .width(60.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        ShimmerEffect(
                            modifier = Modifier
                                .width(50.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        ShimmerEffect(
                            modifier = Modifier
                                .width(40.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Divider shimmer
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Contact info rows
            repeat(3) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ShimmerEffect(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }
                if (it < 2) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ShimmerLoadingList(
    itemCount: Int = 5,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(itemCount) {
            ShimmerUserCard()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ShimmerUserCardPreview() {
    PagingTheme {
        ShimmerUserCard(
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ShimmerLoadingListPreview() {
    PagingTheme {
        ShimmerLoadingList(itemCount = 3)
    }
}
