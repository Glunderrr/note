package files.app.note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import files.app.data.repository.UserRepositoryImpl
import files.app.data.storage.SharedprefUserStorage
import files.app.note.presentation.ui.theme.NoteTheme
import files.domain.models.UserModel
import files.domain.usecases.GetDataUseCase
import files.domain.usecases.SaveDataUseCase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteTheme {
                val userStorage = SharedprefUserStorage(applicationContext)
                val userRepository = UserRepositoryImpl(userStorage)
                val getData = GetDataUseCase(userRepository)
                val saveData = SaveDataUseCase(userRepository)

                val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

                val getDataTextState = remember { mutableStateOf("") }
                val nameState = remember { mutableStateOf("") }
                val lastNameState = remember { mutableStateOf("") }

                Screen(
                    dataText = getDataTextState,
                    onGetClick = {
                        val newUserData = getData.execute()
                        getDataTextState.value =
                            newUserData.name + " " + newUserData.lastName
                    },
                    onSaveClick = {
                        saveData.execute(
                            UserModel(
                                nameState.value,
                                lastNameState.value
                            )
                        )
                    },
                    nameState = nameState,
                    lastNameState = lastNameState
                )
            }
        }
    }
}


@Composable
fun Screen(
    dataText: MutableState<String>,
    onGetClick: () -> Unit,
    nameState: MutableState<String>,
    lastNameState: MutableState<String>,
    onSaveClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        Text(
            text = dataText.value.ifBlank { "No data" },
            modifier = Modifier.fillMaxWidth(0.9f),
        )
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(0.9f),
            onClick = { onGetClick.invoke() }
        ) {
            Text("GET DATA")
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.9f),
            value = nameState.value,
            onValueChange = { name ->
                nameState.value = name
            },
            maxLines = 1,
            textStyle = TextStyle(color = Color.Black),
            placeholder = { Text(text = "Put name here") }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.9f),
            value = lastNameState.value,
            onValueChange = { name ->
                lastNameState.value = name
            },
            maxLines = 1,
            textStyle = TextStyle(color = Color.Black),
            placeholder = { Text(text = "Put last name here") }
        )
        OutlinedButton(
            onClick = { onSaveClick.invoke() },
            modifier = Modifier.fillMaxWidth(0.9f),
        ) {
            Text("SAVE DATA")
        }
    }
}