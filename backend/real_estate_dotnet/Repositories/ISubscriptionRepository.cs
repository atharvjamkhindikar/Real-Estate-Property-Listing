
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Repositories
{
    public interface ISubscriptionRepository
    {
        Task<Subscription?> GetByUserIdAsync(long userId);

        Task<List<Subscription>> GetByPlanTypeAsync(SubscriptionType planType);

        Task<List<Subscription>> GetActiveSubscriptionsAsync();
        Task<List<Subscription>> GetInactiveSubscriptionsAsync();

        Task<List<Subscription>> GetByEndDateBeforeAsync(DateOnly date);
        Task<List<Subscription>> GetByEndDateBetweenAsync(DateOnly start, DateOnly end);

        Task<List<Subscription>> GetExpiredSubscriptionsAsync(DateOnly date);
        Task<List<Subscription>> GetSubscriptionsToRenewAsync(DateOnly start, DateOnly end);

        Task<long> CountActiveByPlanTypeAsync(SubscriptionType planType);

        Task<bool> ExistsByUserIdAsync(long userId);
    }
}
