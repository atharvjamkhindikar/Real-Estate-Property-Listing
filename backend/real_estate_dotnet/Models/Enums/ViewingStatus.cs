using real_estate_dotnet.Models.Enums;
using real_estate_dotnet.Models;


namespace real_estate_dotnet.Models
{
    public enum ViewingStatus
    {
        Pending,     // Viewing request submitted, awaiting confirmation
        Confirmed,   // Viewing confirmed by agent/owner
        Rejected,    // Viewing request rejected
        Completed,   // Viewing was completed
        Cancelled    // Viewing was cancelled by user
    }
}
