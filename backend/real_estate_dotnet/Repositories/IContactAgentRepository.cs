using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Repositories
{
    public interface IContactAgentRepository
    {
        Task<List<ContactAgent>> GetByPropertyIdAsync(long propertyId);
        Task<List<ContactAgent>> GetByUserIdAsync(long userId);

        Task<List<ContactAgent>> GetByPropertyIdOrderByCreatedAtDescAsync(long propertyId);
        Task<List<ContactAgent>> GetByUserIdOrderByCreatedAtDescAsync(long userId);

        Task<List<ContactAgent>> GetUnreadAsync();
        Task<List<ContactAgent>> GetUnreadByPropertyOwnerIdAsync(long ownerId);

        Task<List<ContactAgent>> GetAllForPropertyOwnerAsync(long ownerId);
        Task<List<ContactAgent>> GetInDateRangeAsync(DateTime startDate, DateTime endDate);

        Task<long> CountByPropertyIdAfterDateAsync(long propertyId, DateTime startDate);
        Task<long> CountUnreadByPropertyOwnerIdAsync(long ownerId);

        Task AddAsync(ContactAgent contactAgent);
        Task SaveChangesAsync();
    }
}
