using Microsoft.EntityFrameworkCore;
using real_estate_dotnet.Data;
using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Repositories
{
    public class BuilderGroupRepository : IBuilderGroupRepository
    {
        private readonly ApplicationDbContext _context;

        public BuilderGroupRepository(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<BuilderGroup?> GetByNameAsync(string name)
        {
            return await _context.BuilderGroups
                .FirstOrDefaultAsync(bg => bg.Name == name);
        }

        public async Task<BuilderGroup?> GetByNameIgnoreCaseAsync(string name)
        {
            return await _context.BuilderGroups
                .FirstOrDefaultAsync(bg => bg.Name.ToLower() == name.ToLower());
        }

        public async Task<List<BuilderGroup>> GetByActiveAsync(bool active)
        {
            return await _context.BuilderGroups
                .Where(bg => bg.Active == active)
                .ToListAsync();
        }

        public async Task<List<BuilderGroup>> GetAllOrderByNameAsync()
        {
            return await _context.BuilderGroups
                .OrderBy(bg => bg.Name)
                .ToListAsync();
        }

        public async Task AddAsync(BuilderGroup builderGroup)
        {
            await _context.BuilderGroups.AddAsync(builderGroup);
        }

        public async Task SaveChangesAsync()
        {
            await _context.SaveChangesAsync();
        }
    }
}
