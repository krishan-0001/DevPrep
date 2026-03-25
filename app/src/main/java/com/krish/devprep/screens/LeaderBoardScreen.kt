package com.krish.devprep.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.krish.devprep.data.User
import com.krish.devprep.data.viewmodel.LeaderboardViewModel

@Composable
fun LeaderBoardScreen(viewModel: LeaderboardViewModel = viewModel()) {

    val users = viewModel.users

    Box(modifier = Modifier.fillMaxSize()
        .background(Brush.verticalGradient(
            listOf(Color(0XFF2E1A47),Color(0XFF4A2C6D)))
        )
    ){


        Column(
            modifier = Modifier.fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Leaderboard",
                fontSize = 26.sp,
                color = Color.White)
            Spacer(modifier = Modifier.height(24.dp))
            if(users.size>=3){
                TopThreeUsers(users)
            }
            Spacer(modifier = Modifier.height(24.dp))
            LeaderboardList(users.drop(3))
        }
    }

}

@Composable
fun TopThreeUsers(topUsers: List<User>){

    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly){

        TopUserCard(topUsers[1],rank = 2,small = true)
        TopUserCard(topUsers[0],rank = 1,small = false)
        TopUserCard(topUsers[2],rank = 3,small = true)
    }
}

@Composable
fun TopUserCard(user: User,rank: Int,small: Boolean){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.padding(8.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
                .size(if(small) 80.dp else 100.dp),
            contentAlignment = Alignment.Center
        ){
            AsyncImage(
                model = user.profileImage,
                contentDescription = "Profile Image",
                modifier = Modifier.size(60.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = user.name,color = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = user.score.toString(),color = Color.Yellow,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Podium(){

    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom){

        PodiumBlock("2",80.dp)
        PodiumBlock("1",120.dp)
        PodiumBlock("3",60.dp)
    }
}

@Composable
fun PodiumBlock(rank: String,height: Dp){

    Box(
        modifier = Modifier.width(60.dp)
            .height(height)
            .background(Color(0XFFFFC107), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ){

        Text(text = rank, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun LeaderboardList(users: List<User>){

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        itemsIndexed(users){ index,user->
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.White,RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text("${index+4}", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(12.dp))

                AsyncImage(
                    model = user.profileImage,
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))

                Text(text = user.name)

                Spacer(modifier = Modifier.weight(1f))

                Text(text = user.score.toString(),
                    color = Color(0XFFFF9800),
                    fontWeight = FontWeight.Bold)

            }
        }
    }
}