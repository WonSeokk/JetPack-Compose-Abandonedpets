package wwon.seokk.abandonedpets.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import wwon.seokk.abandonedpets.R
import wwon.seokk.abandonedpets.data.remote.model.request.GetAbandonmentPublicRequest
import wwon.seokk.abandonedpets.ui.base.ScreenState
import wwon.seokk.abandonedpets.ui.theme.AbandonedPetsTheme
/**
 * Created by WonSeok on 2022.08.15
 **/
@Composable
fun PetSearchContent(
    widthSize: WindowWidthSizeClass,
    uiState: HomeState,
    openPetRegionSearch: (GetAbandonmentPublicRequest) -> Unit,
    openPetKindSearch: (GetAbandonmentPublicRequest) -> Unit
) {
    val columns = when (widthSize) {
        WindowWidthSizeClass.Compact -> 1
        WindowWidthSizeClass.Medium -> 2
        WindowWidthSizeClass.Expanded -> 4
        else -> 1
    }

    PetSearch(
        columns = columns,
        content = {
            item {
                SearchInput("지역", uiState, openPetRegionSearch)
            }
            item {
                SearchInput("품종", uiState, openPetKindSearch)
            }
            item {
                SearchInput("", uiState, openPetRegionSearch)
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SearchInput(
    title: String,
    uiState: HomeState?,
    openScreen: (GetAbandonmentPublicRequest) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(AbandonedPetsTheme.shapes.mediumRoundCornerShape),
        onClick = {
            openScreen.invoke(uiState!!.requestQuery.value)
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .padding(24.dp, 0.dp, 0.dp, 0.dp)
                    .weight(0.5f),
                text = title,
                style = AbandonedPetsTheme.typography.title1,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                fontSize = 18.sp,
            )
            Row(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 5.dp, 0.dp)
                    .weight(0.5f),
                horizontalArrangement = Arrangement.End
            ){
                SearchTextFieldText(title, uiState!!.requestQuery.value)
            }
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(0.dp, 0.dp, 12.dp, 0.dp)
            )
        }
    }
}

@Composable
private fun SearchTextFieldText(title: String, request: GetAbandonmentPublicRequest) {
    Text(
        text = when(title) {
            "지역" -> "${request.upr.orgNm} · ${request.org.orgNm} · ${request.shelter.careNm}"
            "품종" -> "${request.upKind.knm} · ${request.kind.knm}"
            else -> "" },
        style = AbandonedPetsTheme.typography.body1
    )
}

@Composable
private fun PetSearch(
    columns: Int,
    content: LazyGridScope.() -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(start = 24.dp, top = 0.dp, end = 24.dp, bottom = 12.dp),
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = content
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchInputPreview() {
    val testUiState = HomeState(ScreenState.Loading, mutableStateOf(GetAbandonmentPublicRequest.EMPTY), null, null)
    SearchInput("지역", testUiState) { }
}