using System;

namespace real_estate_dotnet.Exceptions
{
    public class DuplicateResourceException : Exception
    {
        // Constructor with just a message
        public DuplicateResourceException(string message) : base(message)
        {
        }

        // Constructor with resourceName, fieldName, fieldValue
        public DuplicateResourceException(string resourceName, string fieldName, object fieldValue)
            : base($"{resourceName} already exists with {fieldName}: '{fieldValue}'")
        {
        }
    }
}
