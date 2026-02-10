using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using real_estate_dotnet.Data;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Repositories
{
    public class UserRepository : IUserRepository
    {
        private readonly ApplicationDbContext _context;

        public UserRepository(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<User?> GetByEmailAsync(string email)
        {
            return await _context.Users
                .FirstOrDefaultAsync(u => u.Email == email);
        }

        public async Task<User?> GetByEmailAndActiveTrueAsync(string email)
        {
            return await _context.Users
                .FirstOrDefaultAsync(u => u.Email == email && u.Active);
        }

        public async Task<List<User>> GetByUserTypeAsync(UserType userType)
        {
            return await _context.Users
                .Where(u => u.UserType == userType)
                .ToListAsync();
        }

        public async Task<List<User>> GetActiveUsersAsync()
        {
            return await _context.Users
                .Where(u => u.Active)
                .ToListAsync();
        }

        public async Task<List<User>> GetByUserTypeAndActiveTrueAsync(UserType userType)
        {
            return await _context.Users
                .Where(u => u.UserType == userType && u.Active)
                .ToListAsync();
        }
    }
}
