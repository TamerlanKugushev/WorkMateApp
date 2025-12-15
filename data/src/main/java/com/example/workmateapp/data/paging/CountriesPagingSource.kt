package com.example.workmateapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.workmateapp.data.datasource.LocalDataSource
import com.example.workmateapp.domain.model.Country

class CountriesPagingSource(
    private val localDataSource: LocalDataSource
) : PagingSource<Int, Country>() {
    
    override fun getRefreshKey(state: PagingState<Int, Country>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
    
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Country> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val offset = page * pageSize
            
            val countries = localDataSource.getCountriesPaged(
                limit = pageSize,
                offset = offset
            )
            
            val totalCount = localDataSource.getCountriesCount()
            val hasNextPage = offset + countries.size < totalCount
            
            LoadResult.Page(
                data = countries,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (hasNextPage) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
