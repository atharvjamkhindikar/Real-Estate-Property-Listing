using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Repositories
{
    public interface IBuilderGroupRepository
    {
        Task<BuilderGroup?> GetByNameAsync(string name);
        Task<BuilderGroup?> GetByNameIgnoreCaseAsync(string name);
        Task<List<BuilderGroup>> GetByActiveAsync(bool active);
        Task<List<BuilderGroup>> GetAllOrderByNameAsync();

        Task AddAsync(BuilderGroup builderGroup);
        Task SaveChangesAsync();
    }
}
