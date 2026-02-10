using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Repositories
{
    public interface IPropertyImageRepository
    {
        Task<List<PropertyImage>> GetByPropertyIdAsync(long propertyId);
        Task<List<PropertyImage>> GetByPropertyIdOrderedAsync(long propertyId);
        Task<List<PropertyImage>> GetPrimaryByPropertyIdAsync(long propertyId);

        Task<int?> GetMaxDisplayOrderAsync(long propertyId);

        Task<List<PropertyImage>> GetByPropertyIdWithPropertyAsync(long propertyId);

        Task AddAsync(PropertyImage image);
        Task SaveChangesAsync();
    }
}
