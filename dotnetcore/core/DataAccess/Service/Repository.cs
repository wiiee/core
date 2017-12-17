namespace DataAccess.Service
{
    using DataAccess.Database.Base;
    using DataAccess.Database.Manager;
    using Microsoft.Extensions.Logging;
    using Platform.Data;
    using Platform.Util;
    using System;
    using System.Collections.Concurrent;

    public class Repository<T>
        where T : IEntity
    {
        private ConcurrentDictionary<string, IDatabase> dbs;
        private ConcurrentDictionary<string, IDbCollection<T>> collections;

        private static ILogger _logger = LoggerUtil.CreateLogger<Repository<T>>();

        private Repository()
        {
            dbs = new ConcurrentDictionary<string, IDatabase>();
            collections = new ConcurrentDictionary<string, IDbCollection<T>>();
        }

        private static readonly Lazy<Repository<T>> lazy = new Lazy<Repository<T>>(() => new Repository<T>());

        public static Repository<T> Instance { get { return lazy.Value; } }

        public IDbCollection<T> GetCollection(string dbName)
        {
            return collections.GetOrAdd(GetCollectionKey(dbName), GetDb(dbName).GetCollection<T>());
        }

        private IDatabase GetDb(string dbName)
        {
            return dbs.GetOrAdd(dbName, DatabaseManager.Instance.GetDatabase(dbName));
        }

        private string GetCollectionKey(string dbName)
        {
            return dbName + "." + typeof(T).FullName;
        }

        public void RenameCollection(string dbName, string oldName, string newName)
        {
            GetDb(dbName).RenameCollection(oldName, newName);
        }

        public void DropCollection(string dbName)
        {
            GetDb(dbName).DropCollection<T>();
        }
    }
}
