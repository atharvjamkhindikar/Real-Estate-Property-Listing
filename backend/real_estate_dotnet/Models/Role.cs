namespace RealEstate.Model
{
    public enum Role
    {
        Guest,      // Can only view properties
        User,       // Can register, login, save favorites, search
        Subscriber, // User + AI suggestions (future)
        Admin       // Full access - manage properties, users, listings
    }
}
