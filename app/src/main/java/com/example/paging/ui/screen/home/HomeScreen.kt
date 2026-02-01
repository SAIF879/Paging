package com.example.paging.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.paging.domain.model.*
import com.example.paging.ui.components.ShimmerLoadingList
import com.example.paging.ui.components.ShimmerUserCard
import com.example.paging.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Users",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is UiState.Loading -> {
                    ShimmerLoadingList(itemCount = 5)
                }
                is UiState.Success -> {
                    UserListContent(
                        users = state.users,
                        isLoadingMore = state.isLoadingMore,
                        canLoadMore = state.canLoadMore,
                        onLoadMore = { viewModel.loadMoreUsers() }
                    )
                }
                is UiState.Error -> {
                    ErrorContent(
                        message = state.message,
                        onRetry = { viewModel.retry() }
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "⚠️",
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = "Oops! Something went wrong",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = ErrorRed
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = DarkOnSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Retry")
            }
        }
    }
}

@Composable
private fun UserListContent(
    users: List<User>,
    isLoadingMore: Boolean,
    canLoadMore: Boolean,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()

    // Detect when user scrolls near the end
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem >= totalItems - 3 && canLoadMore && !isLoadingMore
        }
    }

    // Trigger load more when needed
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onLoadMore()
        }
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(users, key = { it.uuid }) { user ->
            UserCard(user = user)
        }

        // Loading more indicator at the bottom
        if (isLoadingMore) {
            item {
                ShimmerUserCard(
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        // End of list indicator
        if (!canLoadMore && users.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "You've reached the end 🎉",
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarkOnSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun UserCard(user: User) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkCardBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with Avatar and Name
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(DarkPrimary, DarkSecondary)
                            )
                        )
                ) {
                    AsyncImage(
                        model = user.picture.medium,
                        contentDescription = "Profile picture of ${user.name.fullName}",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Name and basic info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = user.name.fullName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = DarkOnSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Gender badge
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (user.gender == "male") AccentBlue.copy(alpha = 0.2f)
                            else DarkPrimary.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = user.gender.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.labelSmall,
                                color = if (user.gender == "male") AccentBlue else DarkPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        // Age badge
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = AccentGreen.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "${user.dateOfBirth.age} yrs",
                                style = MaterialTheme.typography.labelSmall,
                                color = AccentGreen,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        // Nationality
                        Text(
                            text = "🌍 ${user.nationality}",
                            style = MaterialTheme.typography.labelSmall,
                            color = DarkOnSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                color = DarkOnSurfaceVariant.copy(alpha = 0.2f),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Contact info
            ContactInfoRow(
                icon = Icons.Default.Email,
                text = user.email,
                iconTint = DarkPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            ContactInfoRow(
                icon = Icons.Default.Phone,
                text = user.phone,
                iconTint = AccentGreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            ContactInfoRow(
                icon = Icons.Default.LocationOn,
                text = "${user.location.city}, ${user.location.country}",
                iconTint = AccentBlue
            )
        }
    }
}

@Composable
private fun ContactInfoRow(
    icon: ImageVector,
    text: String,
    iconTint: androidx.compose.ui.graphics.Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = DarkOnSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// ============ PREVIEW ============

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun UserCardPreview() {
    PagingTheme {
        UserCard(
            user = User(
                uuid = "preview-uuid-123",
                gender = "female",
                name = Name(title = "Miss", first = "Laura", last = "Woods"),
                location = Location(
                    street = Street(number = 2479, name = "Henry Street"),
                    city = "Blessington",
                    state = "Wexford",
                    country = "Ireland",
                    coordinates = Coordinates(latitude = 2.0565, longitude = 95.2422),
                    timezone = Timezone(offset = "+1:00", description = "Brussels, Copenhagen, Madrid, Paris")
                ),
                email = "laura.woods@example.com",
                phone = "031-623-5189",
                cell = "081-807-8083",
                dateOfBirth = DateInfo(date = "1967-07-23T09:18:33.666Z", age = 58),
                registered = DateInfo(date = "2018-10-18T04:05:51.990Z", age = 7),
                picture = Picture(
                    large = "https://randomuser.me/api/portraits/women/88.jpg",
                    medium = "https://randomuser.me/api/portraits/med/women/88.jpg",
                    thumbnail = "https://randomuser.me/api/portraits/thumb/women/88.jpg"
                ),
                nationality = "IE"
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun UserListPreview() {
    val mockUsers = listOf(
        User(
            uuid = "1",
            gender = "female",
            name = Name(title = "Miss", first = "Laura", last = "Woods"),
            location = Location(
                street = Street(number = 2479, name = "Henry Street"),
                city = "Blessington",
                state = "Wexford",
                country = "Ireland",
                coordinates = Coordinates(latitude = 2.0565, longitude = 95.2422),
                timezone = Timezone(offset = "+1:00", description = "Brussels")
            ),
            email = "laura.woods@example.com",
            phone = "031-623-5189",
            cell = "081-807-8083",
            dateOfBirth = DateInfo(date = "1967-07-23", age = 58),
            registered = DateInfo(date = "2018-10-18", age = 7),
            picture = Picture(
                large = "https://randomuser.me/api/portraits/women/88.jpg",
                medium = "https://randomuser.me/api/portraits/med/women/88.jpg",
                thumbnail = "https://randomuser.me/api/portraits/thumb/women/88.jpg"
            ),
            nationality = "IE"
        ),
        User(
            uuid = "2",
            gender = "male",
            name = Name(title = "Mr", first = "Marten", last = "Faber"),
            location = Location(
                street = Street(number = 6167, name = "Grüner Weg"),
                city = "Falkenberg",
                state = "Thüringen",
                country = "Germany",
                coordinates = Coordinates(latitude = 89.4367, longitude = 135.6354),
                timezone = Timezone(offset = "+5:45", description = "Kathmandu")
            ),
            email = "marten.faber@example.com",
            phone = "0100-8354415",
            cell = "0172-4195644",
            dateOfBirth = DateInfo(date = "1960-08-01", age = 65),
            registered = DateInfo(date = "2002-04-03", age = 23),
            picture = Picture(
                large = "https://randomuser.me/api/portraits/men/1.jpg",
                medium = "https://randomuser.me/api/portraits/med/men/1.jpg",
                thumbnail = "https://randomuser.me/api/portraits/thumb/men/1.jpg"
            ),
            nationality = "DE"
        )
    )

    PagingTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DarkBackground
        ) {
            UserListContent(
                users = mockUsers,
                isLoadingMore = false,
                canLoadMore = true,
                onLoadMore = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ShimmerLoadingPreview() {
    PagingTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DarkBackground
        ) {
            ShimmerLoadingList(itemCount = 3)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun ErrorPreview() {
    PagingTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DarkBackground
        ) {
            ErrorContent(
                message = "Unable to connect to the server. Please check your internet connection.",
                onRetry = {}
            )
        }
    }
}
