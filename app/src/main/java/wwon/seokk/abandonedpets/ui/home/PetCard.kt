package wwon.seokk.abandonedpets.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import wwon.seokk.abandonedpets.R
import wwon.seokk.abandonedpets.domain.entity.abandonmentpublic.AbandonmentPublicResultEntity
import wwon.seokk.abandonedpets.ui.theme.AbandonedPetsTheme
import wwon.seokk.abandonedpets.util.noticeDateFormatter

/**
 * Created by WonSeok on 2022.08.15
 **/
@Composable
fun PetCard(
    pet: AbandonmentPublicResultEntity,
    petClick: (AbandonmentPublicResultEntity) -> Unit
) {
    Card(
        elevation = 0.dp,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(120.dp)
            .clickable(
                enabled = true,
                onClick = {
                    petClick(pet)
                })
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)
            ) {
                Row {
                    PetState(pet)
                    PetNoticeDate(pet)
                }
                PetInfo(pet)
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    PetShelter(pet)
                    PetPlace(pet)
                }
            }
            PetImage(pet)
        }
    }
}

@Composable
private fun PetState(pet: AbandonmentPublicResultEntity) {
    Text(text = pet.processState,
        style = AbandonedPetsTheme.typography.body1,
        color = if(pet.processState == "보호중")
            AbandonedPetsTheme.colors.greenColor
        else
            AbandonedPetsTheme.colors.redColor,
        modifier = Modifier.padding(end = 8.dp))
}

@Composable
private fun PetNoticeDate(pet: AbandonmentPublicResultEntity) {
    Text(text = noticeDateFormatter(pet.noticeSdt, pet.noticeEdt),
        style = AbandonedPetsTheme.typography.body1)
}

@Composable
private fun PetInfo(pet: AbandonmentPublicResultEntity) {
    val petKind = if(pet.kindCd.isNotBlank()) "${pet.kindCd} | " else ""
    val petColor = if(pet.colorCd.isNotBlank()) "${pet.colorCd} | " else ""
    Text(text = "$petKind$petColor${pet.age}",
        style = AbandonedPetsTheme.typography.body1)
}

@Composable
private fun PetShelter(pet: AbandonmentPublicResultEntity) {
    Text(text = pet.careNm,
        style = AbandonedPetsTheme.typography.body1,
        modifier = Modifier
            .padding(top = 5.dp)
    )
}

@Composable
private fun PetPlace(pet: AbandonmentPublicResultEntity) {
    Text(text = pet.happenPlace,
        fontWeight = FontWeight.Bold,
        style = AbandonedPetsTheme.typography.body2)
}

@Composable
private fun PetImage(pet: AbandonmentPublicResultEntity) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(120.dp)
            .height(120.dp)
            .clip(AbandonedPetsTheme.shapes.largeRoundCornerShape)
    ) {
        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(pet.popfile)
            .crossfade(true)
            .build(),
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(R.drawable.ic_pets),
            contentDescription = stringResource(id = R.string.pet_image_description),
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PetCardPreview() {
    AbandonedPetsTheme{
        PetCard(
            AbandonmentPublicResultEntity("","","","",
                "","","","","","20220809","20220811","","보호중",
                "","","","","","","","",""),
            petClick ={ }
        )
    }
}