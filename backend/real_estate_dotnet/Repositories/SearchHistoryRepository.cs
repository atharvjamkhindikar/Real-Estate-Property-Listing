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
    public class SearchHistoryRepository : ISearchHistoryRepository
    {
        private readonly ApplicationDbContext _context;

        public SearchHistoryRepository(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<List<SearchHistory>> GetByUserIdAsync(long userId)
        {
            return await _context.SearchHistories
                .Where(sh => sh.UserId == userId)
                .ToListAsync();
        }

        public async Task<List<SearchHistory>> GetByUserIdOrderedAsync(long userId, int? limit = null)
        {
            var query = _context.SearchHistories
                .Where(sh => sh.UserId == userId)
                .OrderByDescending(sh => sh.SearchedAt);

            if (limit.HasValue)
                query = query.Take(limit.Value);

            return await query.ToListAsync();
        }

        public async Task<List<SearchHistory>> GetByDateRangeAsync(DateTime start, DateTime end)
        {
            return await _context.SearchHistories
                .Where(sh => sh.SearchedAt >= start && sh.SearchedAt <= end)
                .ToListAsync();
        }

        public async Task DeleteByUserIdAsync(long userId)
        {
            var histories = await _context.SearchHistories
                .Where(sh => sh.UserId == userId)
                .ToListAsync();

            _context.SearchHistories.RemoveRange(histories);
            await _context.SaveChangesAsync();
        }

        public async Task DeleteBeforeDateAsync(DateTime dateTime)
        {
            var histories = await _context.SearchHistories
                .Where(sh => sh.SearchedAt < dateTime)
                .ToListAsync();

            _context.SearchHistories.RemoveRange(histories);
            await _context.SaveChangesAsync();
        }

        public async Task<List<(string City, long Count)>> GetMostSearchedCitiesAsync(int limit)
        {
            return await _context.SearchHistories
                .Where(sh => sh.SearchCity != null)
                .GroupBy(sh => sh.SearchCity!)
                .OrderByDescending(g => g.Count())
                .Take(limit)
                .Select(g => (g.Key, (long)g.Count()))
                .ToListAsync();
        }

        public async Task<List<(PropertyType Type, long Count)>> GetMostSearchedPropertyTypesAsync()
        {
            return await _context.SearchHistories
                .Where(sh => sh.SearchPropertyType != null)
                .GroupBy(sh => sh.SearchPropertyType!.Value)
                .OrderByDescending(g => g.Count())
                .Select(g => (g.Key, (long)g.Count()))
                .ToListAsync();
        }

        public async Task<List<SearchHistory>> GetByUserIdAndCityAsync(long userId, string city)
        {
            return await _context.SearchHistories
                .Where(sh => sh.UserId == userId && sh.SearchCity == city)
                .ToListAsync();
        }

        public async Task<long> CountByUserIdAsync(long userId)
        {
            return await _context.SearchHistories
                .LongCountAsync(sh => sh.UserId == userId);
        }
    }
}
