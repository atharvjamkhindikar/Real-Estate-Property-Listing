using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Repositories
{
    public interface ISearchHistoryRepository
    {
        Task<List<SearchHistory>> GetByUserIdAsync(long userId);
        Task<List<SearchHistory>> GetByUserIdOrderedAsync(long userId, int? limit = null);

        Task<List<SearchHistory>> GetByDateRangeAsync(DateTime start, DateTime end);

        Task DeleteByUserIdAsync(long userId);
        Task DeleteBeforeDateAsync(DateTime dateTime);

        Task<List<(string City, long Count)>> GetMostSearchedCitiesAsync(int limit);
        Task<List<(PropertyType Type, long Count)>> GetMostSearchedPropertyTypesAsync();

        Task<List<SearchHistory>> GetByUserIdAndCityAsync(long userId, string city);

        Task<long> CountByUserIdAsync(long userId);
    }
}
