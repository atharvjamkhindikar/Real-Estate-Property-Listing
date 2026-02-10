using System.Collections.Generic;
using System.Threading.Tasks;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Repositories
{
    public interface IUserRepository
    {
        Task<User?> GetByEmailAsync(string email);

        Task<User?> GetByEmailAndActiveTrueAsync(string email);

        Task<List<User>> GetByUserTypeAsync(UserType userType);

        Task<List<User>> GetActiveUsersAsync();

        Task<List<User>> GetByUserTypeAndActiveTrueAsync(UserType userType);
    }
}
