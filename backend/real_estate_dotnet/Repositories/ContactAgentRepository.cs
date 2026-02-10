using Microsoft.EntityFrameworkCore;
using real_estate_dotnet.Data;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Repositories
{
    public class ContactAgentRepository : IContactAgentRepository
    {
        private readonly ApplicationDbContext _context;

        public ContactAgentRepository(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<List<ContactAgent>> GetByPropertyIdAsync(long propertyId)
        {
            return await _context.ContactAgents
                .Where(c => c.Property.Id == propertyId)
                .ToListAsync();
        }

        public async Task<List<ContactAgent>> GetByUserIdAsync(long userId)
        {
            return await _context.ContactAgents
                .Where(c => c.User.Id == userId)
                .ToListAsync();
        }

        public async Task<List<ContactAgent>> GetByPropertyIdOrderByCreatedAtDescAsync(long propertyId)
        {
            return await _context.ContactAgents
                .Where(c => c.Property.Id == propertyId)
                .OrderByDescending(c => c.CreatedAt)
                .ToListAsync();
        }

        public async Task<List<ContactAgent>> GetByUserIdOrderByCreatedAtDescAsync(long userId)
        {
            return await _context.ContactAgents
                .Where(c => c.User.Id == userId)
                .OrderByDescending(c => c.CreatedAt)
                .ToListAsync();
        }

        public async Task<List<ContactAgent>> GetUnreadAsync()
        {
            return await _context.ContactAgents
                .Where(c => !c.IsRead)
                .ToListAsync();
        }

        public async Task<List<ContactAgent>> GetUnreadByPropertyOwnerIdAsync(long ownerId)
        {
            return await _context.ContactAgents
                .Where(c => !c.IsRead && c.Property.Owner.Id == ownerId)
                .ToListAsync();
        }

        public async Task<List<ContactAgent>> GetAllForPropertyOwnerAsync(long ownerId)
        {
            return await _context.ContactAgents
                .Where(c => c.Property.Owner.Id == ownerId)
                .OrderByDescending(c => c.CreatedAt)
                .ToListAsync();
        }

        public async Task<List<ContactAgent>> GetInDateRangeAsync(DateTime startDate, DateTime endDate)
        {
            return await _context.ContactAgents
                .Where(c => c.CreatedAt >= startDate && c.CreatedAt <= endDate)
                .OrderByDescending(c => c.CreatedAt)
                .ToListAsync();
        }

        public async Task<long> CountByPropertyIdAfterDateAsync(long propertyId, DateTime startDate)
        {
            return await _context.ContactAgents
                .LongCountAsync(c =>
                    c.Property.Id == propertyId &&
                    c.CreatedAt > startDate);
        }

        public async Task<long> CountUnreadByPropertyOwnerIdAsync(long ownerId)
        {
            return await _context.ContactAgents
                .LongCountAsync(c =>
                    !c.IsRead &&
                    c.Property.Owner.Id == ownerId);
        }

        public async Task AddAsync(ContactAgent contactAgent)
        {
            await _context.ContactAgents.AddAsync(contactAgent);
        }

        public async Task SaveChangesAsync()
        {
            await _context.SaveChangesAsync();
        }
    }
}
