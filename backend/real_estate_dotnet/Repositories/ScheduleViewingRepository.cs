using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using real_estate_dotnet.Data;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Repositories
{
    public class ScheduleViewingRepository : IScheduleViewingRepository
    {
        private readonly ApplicationDbContext _context;

        public ScheduleViewingRepository(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<List<ScheduleViewing>> GetByUserIdAsync(long userId)
        {
            return await _context.ScheduleViewings
                .Where(s => s.UserId == userId)
                .ToListAsync();
        }

        public async Task<List<ScheduleViewing>> GetByPropertyIdAsync(long propertyId)
        {
            return await _context.ScheduleViewings
                .Where(s => s.PropertyId == propertyId)
                .ToListAsync();
        }

        public async Task<List<ScheduleViewing>> GetByUserIdAndStatusAsync(long userId, ViewingStatus status)
        {
            return await _context.ScheduleViewings
                .Where(s => s.UserId == userId && s.Status == status)
                .ToListAsync();
        }

        public async Task<List<ScheduleViewing>> GetByPropertyIdAndStatusAsync(long propertyId, ViewingStatus status)
        {
            return await _context.ScheduleViewings
                .Where(s => s.PropertyId == propertyId && s.Status == status)
                .ToListAsync();
        }

        public async Task<List<ScheduleViewing>> GetByStatusAsync(ViewingStatus status)
        {
            return await _context.ScheduleViewings
                .Where(s => s.Status == status)
                .ToListAsync();
        }

        public async Task<List<ScheduleViewing>> GetUserViewingsOrderedAsync(long userId)
        {
            return await _context.ScheduleViewings
                .Where(s => s.UserId == userId)
                .OrderBy(s => s.ViewingDate)
                .ToListAsync();
        }

        public async Task<List<ScheduleViewing>> GetPropertyViewingsOrderedAsync(long propertyId)
        {
            return await _context.ScheduleViewings
                .Where(s => s.PropertyId == propertyId)
                .OrderBy(s => s.ViewingDate)
                .ToListAsync();
        }

        public async Task<List<ScheduleViewing>> GetViewingsForPropertyOwnerAsync(long ownerId)
        {
            return await _context.ScheduleViewings
                .Include(s => s.Property)
                .Where(s => s.Property.OwnerId == ownerId)
                .OrderBy(s => s.ViewingDate)
                .ToListAsync();
        }

        public async Task<List<ScheduleViewing>> GetViewingsForPropertyOwnerByStatusAsync(long ownerId, ViewingStatus status)
        {
            return await _context.ScheduleViewings
                .Include(s => s.Property)
                .Where(s => s.Property.OwnerId == ownerId && s.Status == status)
                .OrderBy(s => s.ViewingDate)
                .ToListAsync();
        }

        public async Task<List<ScheduleViewing>> GetViewingsInDateRangeAsync(DateTime startDate, DateTime endDate)
        {
            return await _context.ScheduleViewings
                .Where(s => s.ViewingDate >= startDate && s.ViewingDate <= endDate)
                .OrderBy(s => s.ViewingDate)
                .ToListAsync();
        }

        public async Task<List<ScheduleViewing>> GetConflictingViewingsAsync(long propertyId, DateTime viewingDate)
        {
            return await _context.ScheduleViewings
                .Where(s =>
                    s.PropertyId == propertyId &&
                    s.ViewingDate.Date == viewingDate.Date &&
                    (s.Status == ViewingStatus.PENDING || s.Status == ViewingStatus.CONFIRMED))
                .ToListAsync();
        }

        public async Task<long> CountConfirmedViewingsAsync(long propertyId)
        {
            return await _context.ScheduleViewings
                .LongCountAsync(s => s.PropertyId == propertyId && s.Status == ViewingStatus.CONFIRMED);
        }

        public async Task<bool> ExistsAsync(long userId, long propertyId, ViewingStatus status)
        {
            return await _context.ScheduleViewings
                .AnyAsync(s => s.UserId == userId && s.PropertyId == propertyId && s.Status == status);
        }

        public async Task<ScheduleViewing?> GetByUserPropertyAndDateAsync(long userId, long propertyId, DateTime viewingDate)
        {
            return await _context.ScheduleViewings
                .FirstOrDefaultAsync(s =>
                    s.UserId == userId &&
                    s.PropertyId == propertyId &&
                    s.ViewingDate.Date == viewingDate.Date);
        }
    }
}
