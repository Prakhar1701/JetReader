package prakhar.udemy.jetpackcompose.jetreader.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import prakhar.udemy.jetpackcompose.jetreader.repository.FireRepository
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: FireRepository) :
    ViewModel() {}