using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using real_estate_dotnet.Data;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;

namespace real_estate_dotnet.Repositories
{
    public class PropertyRepository : IPropertyRepository
    {
        private readonly ApplicationDbContext _context;

        public PropertyRepository(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<List<Property>> GetAvailableAsync()
        {
            return await _context.Properties
                .Where(p => p.Available)
                .ToListAsync();
        }

        public async Task<List<Property>> GetByCityAsync(string city)
        {
            return await _context.Properties
                .Where(p => p.City.ToLower() == city.ToLower())
                .ToListAsync();
        }

        public async Task<List<Property>> GetByPropertyTypeAsync(PropertyType propertyType)
        {
            return await _context.Properties
                .Where(p => p.PropertyType == propertyType)
                .ToListAsync();
        }

        public async Task<List<Property>> GetByListingTypeAsync(ListingType listingType)
        {
            return await _context.Properties
                .Where(p => p.ListingType == listingType)
                .ToListAsync();
        }

        public async Task<List<Property>> GetByPriceBetweenAsync(decimal min, decimal max)
        {
            return await _context.Properties
                .Where(p => p.Price >= min && p.Price <= max)
                .ToListAsync();
        }

        public async Task<List<Property>> GetByOwnerIdAsync(long ownerId)
        {
            return await _context.Properties
                .Where(p => p.OwnerId == ownerId)
                .ToListAsync();
        }

        public async Task<List<Property>> GetByBuilderGroupIdAsync(long builderGroupId)
        {
            return await _context.Properties
                .Where(p => p.BuilderGroupId == builderGroupId)
                .ToListAsync();
        }

        public async Task<List<Property>> SearchPropertiesAsync(
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
            int size)
        {
            var query = _context.Properties
                .Where(p => p.Available)
                .AsQueryable();

            if (!string.IsNullOrEmpty(city))
                query = query.Where(p => p.City.ToLower().Contains(city.ToLower()));

            if (!string.IsNullOrEmpty(state))
                query = query.Where(p => p.State.ToLower().Contains(state.ToLower()));

            if (propertyType.HasValue)
                query = query.Where(p => p.PropertyType == propertyType);

            if (listingType.HasValue)
                query = query.Where(p => p.ListingType == listingType);

            if (minPrice.HasValue)
                query = query.Where(p => p.Price >= minPrice);

            if (maxPrice.HasValue)
                query = query.Where(p => p.Price <= maxPrice);

            if (minBedrooms.HasValue)
                query = query.Where(p => p.Bedrooms >= minBedrooms);

            if (maxBedrooms.HasValue)
                query = query.Where(p => p.Bedrooms <= maxBedrooms);

            if (minBathrooms.HasValue)
                query = query.Where(p => p.Bathrooms >= minBathrooms);

            if (maxBathrooms.HasValue)
                query = query.Where(p => p.Bathrooms <= maxBathrooms);

            if (minSquareFeet.HasValue)
                query = query.Where(p => p.SquareFeet >= minSquareFeet);

            if (maxSquareFeet.HasValue)
                query = query.Where(p => p.SquareFeet <= maxSquareFeet);

            return await query
                .Skip(page * size)
                .Take(size)
                .ToListAsync();
        }

        public async Task<List<Property>> SearchByKeywordAsync(string keyword, int page, int size)
        {
            return await _context.Properties
                .Where(p =>
                    p.Available &&
                    (p.Title.ToLower().Contains(keyword.ToLower()) ||
                     p.Description.ToLower().Contains(keyword.ToLower()) ||
                     p.City.ToLower().Contains(keyword.ToLower()) ||
                     p.Address.ToLower().Contains(keyword.ToLower())))
                .Skip(page * size)
                .Take(size)
                .ToListAsync();
        }

        public async Task<List<string>> GetAllCitiesAsync()
        {
            return await _context.Properties
                .Where(p => p.Available)
                .Select(p => p.City)
                .Distinct()
                .OrderBy(c => c)
                .ToListAsync();
        }

        public async Task<List<string>> GetAllStatesAsync()
        {
            return await _context.Properties
                .Where(p => p.Available)
                .Select(p => p.State)
                .Distinct()
                .OrderBy(s => s)
                .ToListAsync();
        }

        public async Task<long> CountAvailableAsync()
        {
            return await _context.Properties
                .LongCountAsync(p => p.Available);
        }

        public async Task<long> CountByPropertyTypeAsync(PropertyType type)
        {
            return await _context.Properties
                .LongCountAsync(p => p.PropertyType == type && p.Available);
        }

        public async Task<decimal?> GetAveragePriceByCityAsync(string city)
        {
            return await _context.Properties
                .Where(p => p.City == city && p.Available)
                .AverageAsync(p => (decimal?)p.Price);
        }

        public async Task<List<Property>> GetRecentPropertiesAsync(int size)
        {
            return await _context.Properties
                .Where(p => p.Available)
                .OrderByDescending(p => p.CreatedAt)
                .Take(size)
                .ToListAsync();
        }

        public async Task<List<Property>> GetCheapestForSaleAsync(int size)
        {
            return await _context.Properties
                .Where(p => p.Available && p.ListingType == ListingType.FOR_SALE)
                .OrderBy(p => p.Price)
                .Take(size)
                .ToListAsync();
        }
    }
}
