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
    public class SubscriptionRepository : ISubscriptionRepository
    {
        private readonly ApplicationDbContext _context;

        public SubscriptionRepository(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<Subscription?> GetByUserIdAsync(long userId)
        {
            return await _context.Subscriptions
                .FirstOrDefaultAsync(s => s.UserId == userId);
        }

        public async Task<List<Subscription>> GetByPlanTypeAsync(SubscriptionType planType)
        {
            return await _context.Subscriptions
                .Where(s => s.PlanType == planType)
                .ToListAsync();
        }

        public async Task<List<Subscription>> GetActiveSubscriptionsAsync()
        {
            return await _context.Subscriptions
                .Where(s => s.Active)
                .ToListAsync();
        }

        public async Task<List<Subscription>> GetInactiveSubscriptionsAsync()
        {
            return await _context.Subscriptions
                .Where(s => !s.Active)
                .ToListAsync();
        }

        public async Task<List<Subscription>> GetByEndDateBeforeAsync(DateOnly date)
        {
            return await _context.Subscriptions
                .Where(s => s.EndDate != null && s.EndDate < date)
                .ToListAsync();
        }

        public async Task<List<Subscription>> GetByEndDateBetweenAsync(DateOnly start, DateOnly end)
        {
            return await _context.Subscriptions
                .Where(s => s.EndDate != null && s.EndDate >= start && s.EndDate <= end)
                .ToListAsync();
        }

        public async Task<List<Subscription>> GetExpiredSubscriptionsAsync(DateOnly date)
        {
            return await _context.Subscriptions
                .Where(s => s.Active && s.EndDate != null && s.EndDate < date)
                .ToListAsync();
        }

        public async Task<List<Subscription>> GetSubscriptionsToRenewAsync(DateOnly start, DateOnly end)
        {
            return await _context.Subscriptions
                .Where(s => s.AutoRenew &&
                            s.EndDate != null &&
                            s.EndDate >= start &&
                            s.EndDate <= end)
                .ToListAsync();
        }

        public async Task<long> CountActiveByPlanTypeAsync(SubscriptionType planType)
        {
            return await _context.Subscriptions
                .LongCountAsync(s => s.PlanType == planType && s.Active);
        }

        public async Task<bool> ExistsByUserIdAsync(long userId)
        {
            return await _context.Subscriptions
                .AnyAsync(s => s.UserId == userId);
        }
    }
}
