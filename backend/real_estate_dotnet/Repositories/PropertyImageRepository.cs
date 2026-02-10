using Microsoft.EntityFrameworkCore;
using real_estate_dotnet.Data;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;

namespace real_estate_dotnet.Repositories
{
    public class PropertyImageRepository : IPropertyImageRepository
    {
        private readonly ApplicationDbContext _context;

        public PropertyImageRepository(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<List<PropertyImage>> GetByPropertyIdAsync(long propertyId)
        {
            return await _context.PropertyImages
                .Where(pi => pi.Property.Id == propertyId)
                .ToListAsync();
        }

        public async Task<List<PropertyImage>> GetByPropertyIdOrderedAsync(long propertyId)
        {
            return await _context.PropertyImages
                .Where(pi => pi.Property.Id == propertyId)
                .OrderBy(pi => pi.DisplayOrder)
                .ToListAsync();
        }

        public async Task<List<PropertyImage>> GetPrimaryByPropertyIdAsync(long propertyId)
        {
            return await _context.PropertyImages
                .Where(pi => pi.Property.Id == propertyId && pi.IsPrimary)
                .ToListAsync();
        }

        public async Task<int?> GetMaxDisplayOrderAsync(long propertyId)
        {
            return await _context.PropertyImages
                .Where(pi => pi.Property.Id == propertyId)
                .MaxAsync(pi => (int?)pi.DisplayOrder);
        }

        public async Task<List<PropertyImage>> GetByPropertyIdWithPropertyAsync(long propertyId)
        {
            return await _context.PropertyImages
                .Include(pi => pi.Property)
                .Where(pi => pi.Property.Id == propertyId)
                .ToListAsync();
        }

        public async Task AddAsync(PropertyImage image)
        {
            await _context.PropertyImages.AddAsync(image);
        }

        public async Task SaveChangesAsync()
        {
            await _context.SaveChangesAsync();
        }
    }
}
