import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ucp_2.R
import com.example.ucp_2.ui.theme.FormViewModel
import com.example.ucp_2.ui.theme.HalamanForm
import com.example.ucp_2.ui.theme.HalamanHome
import com.example.ucp_2.ui.theme.HalamanSatu

enum class PengelolaHalaman {
    Home,
    Dosen,
    Form,
    Summary
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DosenApp(
    bisaNavigasiBack: Boolean,
    navigasiUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (bisaNavigasiBack) {
                IconButton(onClick = navigasiUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )

                }
            }
        }

    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DosenApp(
    viewModel: FormViewModel = ViewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            DosenApp(
                bisaNavigasiBack = false,
                navigasiUp = { /*TODO*/
                }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.stateUI.collectAsState()
        val nameState by viewModel.nameST.collectAsState()
        NavHost(
            navController = navController,
            startDestination = PengelolaHalaman.Home.name,
            modifier = Modifier.padding(innerPadding)
        )
        {
            composable(route = PengelolaHalaman.Home.name) {
                HalamanHome(
                    onNextButtonClicked = {
                        navController.navigate(PengelolaHalaman.Form.name)
                    })

            }
            composable(route = PengelolaHalaman.Form.name) {
                HalamanForm(
                    onSubmitButtonClicked = {
                        viewModel.setNama(it)
                        navController.navigate(PengelolaHalaman.Dosen.name)
                    },
                    onBackButtonCLicked = {
                        navController.popBackStack()
                    })
            }
            composable(route = PengelolaHalaman.Dosen.name) {
                val context = LocalContext.current
                HalamanSatu(
                    pilihDosen = dosen.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setDosen(it) },
                    onConfirmButtonClicked = { viewModel.setNama(it) },
                    onNextButtonClicked = { navController.navigate(PengelolaHalaman.Summary.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToHome(
                            viewModel,
                            navController
                        )
                    })
            }
            composable(route = PengelolaHalaman.Summary.name) {
                HalamanDua(
                    orderUIState = uiState,
                    formState = nameState,
                    onCancelButtonClicked = { cancelOrderAndNavigateToRasa(navController) })
            }
        }

    }


}

private fun cancelOrderAndNavigateToHome(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(PengelolaHalaman.Home.name, inclusive = false)
}

private fun cancelOrderAndNavigateToRasa(
    navController: NavHostController
) {
    navController.popBackStack(PengelolaHalaman.Rasa.name, inclusive = false)

}