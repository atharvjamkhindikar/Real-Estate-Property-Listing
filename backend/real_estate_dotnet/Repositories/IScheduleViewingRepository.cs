using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;

using Microsoft.EntityFrameworkCore;

namespace real_estate_dotnet.Repositories
{
    public interface IScheduleViewingRepository
    {
        Task<List<ScheduleViewing>> GetByUserIdAsync(long userId);
        Task<List<ScheduleViewing>> GetByPropertyIdAsync(long propertyId);

        Task<List<ScheduleViewing>> GetByUserIdAndStatusAsync(long userId, ViewingStatus status);
        Task<List<ScheduleViewing>> GetByPropertyIdAndStatusAsync(long propertyId, ViewingStatus status);

        Task<List<ScheduleViewing>> GetByStatusAsync(ViewingStatus status);

        Task<List<ScheduleViewing>> GetUserViewingsOrderedAsync(long userId);
        Task<List<ScheduleViewing>> GetPropertyViewingsOrderedAsync(long propertyId);

        Task<List<ScheduleViewing>> GetViewingsForPropertyOwnerAsync(long ownerId);
        Task<List<ScheduleViewing>> GetViewingsForPropertyOwnerByStatusAsync(long ownerId, ViewingStatus status);

        Task<List<ScheduleViewing>> GetViewingsInDateRangeAsync(DateTime startDate, DateTime endDate);

        Task<List<ScheduleViewing>> GetConflictingViewingsAsync(long propertyId, DateTime viewingDate);

        Task<long> CountConfirmedViewingsAsync(long propertyId);

        Task<bool> ExistsAsync(long userId, long propertyId, ViewingStatus status);

        Task<ScheduleViewing?> GetByUserPropertyAndDateAsync(long userId, long propertyId, DateTime viewingDate);
    }
}
