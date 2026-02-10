using System.Collections.Generic;
using System.Threading.Tasks;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Repositories
{
    public interface IPropertyRepository
    {
        Task<List<Property>> GetAvailableAsync();
        Task<List<Property>> GetByCityAsync(string city);
        Task<List<Property>> GetByPropertyTypeAsync(PropertyType propertyType);
        Task<List<Property>> GetByListingTypeAsync(ListingType listingType);
        Task<List<Property>> GetByPriceBetweenAsync(decimal min, decimal max);

        Task<List<Property>> GetByOwnerIdAsync(long ownerId);
        Task<List<Property>> GetByBuilderGroupIdAsync(long builderGroupId);

        Task<List<Property>> SearchPropertiesAsync(
            string? city,
            string? state,
            PropertyType? propertyType,
            ListingType? listingType,
            decimal? minPrice,
            decimal? maxPrice,
            int? minBedrooms,
            int? maxBedrooms,
            int? minBathrooms,
            int? maxBathrooms,
            decimal? minSquareFeet,
            decimal? maxSquareFeet,
            int page,
            int size);

        Task<List<Property>> SearchByKeywordAsync(string keyword, int page, int size);

        Task<long> CountAvailableAsync();
        Task<long> CountByPropertyTypeAsync(PropertyType type);
        Task<decimal?> GetAveragePriceByCityAsync(string city);

        Task<List<string>> GetAllCitiesAsync();
        Task<List<string>> GetAllStatesAsync();

        Task<List<Property>> GetRecentPropertiesAsync(int size);
        Task<List<Property>> GetCheapestForSaleAsync(int size);
    }
}
