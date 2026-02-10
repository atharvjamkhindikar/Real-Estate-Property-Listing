using Microsoft.EntityFrameworkCore;
using real_estate_dotnet.Data;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Repositories
{
    public class FavoriteRepository : IFavoriteRepository
    {
        private readonly ApplicationDbContext _context;

        public FavoriteRepository(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<List<Favorite>> GetByUserAsync(User user)
        {
            return await _context.Favorites
                .Where(f => f.User.Id == user.Id)
                .ToListAsync();
        }

        public async Task<List<Favorite>> GetByUserIdAsync(long userId)
        {
            return await _context.Favorites
                .Where(f => f.User.Id == userId)
                .ToListAsync();
        }

        public async Task<List<Favorite>> GetByUserIdPagedAsync(long userId, int pageNumber, int pageSize)
        {
            return await _context.Favorites
                .Where(f => f.User.Id == userId)
                .Skip((pageNumber - 1) * pageSize)
                .Take(pageSize)
                .ToListAsync();
        }

        public async Task<List<Favorite>> GetByPropertyAsync(Property property)
        {
            return await _context.Favorites
                .Where(f => f.Property.Id == property.Id)
                .ToListAsync();
        }

        public async Task<List<Favorite>> GetByPropertyIdAsync(long propertyId)
        {
            return await _context.Favorites
                .Where(f => f.Property.Id == propertyId)
                .ToListAsync();
        }

        public async Task<Favorite?> GetByUserAndPropertyAsync(User user, Property property)
        {
            return await _context.Favorites
                .FirstOrDefaultAsync(f =>
                    f.User.Id == user.Id &&
                    f.Property.Id == property.Id);
        }

        public async Task<Favorite?> GetByUserIdAndPropertyIdAsync(long userId, long propertyId)
        {
            return await _context.Favorites
                .FirstOrDefaultAsync(f =>
                    f.User.Id == userId &&
                    f.Property.Id == propertyId);
        }

        public async Task<bool> ExistsAsync(long userId, long propertyId)
        {
            return await _context.Favorites
                .AnyAsync(f =>
                    f.User.Id == userId &&
                    f.Property.Id == propertyId);
        }

        public async Task DeleteAsync(long userId, long propertyId)
        {
            var favorite = await GetByUserIdAndPropertyIdAsync(userId, propertyId);
            if (favorite != null)
            {
                _context.Favorites.Remove(favorite);
            }
        }

        public async Task<long> CountByPropertyIdAsync(long propertyId)
        {
            return await _context.Favorites
                .LongCountAsync(f => f.Property.Id == propertyId);
        }

        public async Task<List<Property>> GetFavoritePropertiesByUserIdAsync(long userId)
        {
            return await _context.Favorites
                .Where(f => f.User.Id == userId)
                .Select(f => f.Property)
                .ToListAsync();
        }

        public async Task AddAsync(Favorite favorite)
        {
            await _context.Favorites.AddAsync(favorite);
        }

        public async Task SaveChangesAsync()
        {
            await _context.SaveChangesAsync();
        }
    }
}
