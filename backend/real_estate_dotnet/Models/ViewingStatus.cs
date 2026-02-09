namespace RealEstate.Models.Enums
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
