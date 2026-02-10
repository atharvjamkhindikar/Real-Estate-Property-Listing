using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Repositories
{
    public interface IFavoriteRepository
    {
        Task<List<Favorite>> GetByUserAsync(User user);
        Task<List<Favorite>> GetByUserIdAsync(long userId);

        Task<List<Favorite>> GetByUserIdPagedAsync(long userId, int pageNumber, int pageSize);

        Task<List<Favorite>> GetByPropertyAsync(Property property);
        Task<List<Favorite>> GetByPropertyIdAsync(long propertyId);

        Task<Favorite?> GetByUserAndPropertyAsync(User user, Property property);
        Task<Favorite?> GetByUserIdAndPropertyIdAsync(long userId, long propertyId);

        Task<bool> ExistsAsync(long userId, long propertyId);

        Task DeleteAsync(long userId, long propertyId);

        Task<long> CountByPropertyIdAsync(long propertyId);

        Task<List<Property>> GetFavoritePropertiesByUserIdAsync(long userId);

        Task AddAsync(Favorite favorite);
        Task SaveChangesAsync();
    }
}
