﻿namespace DataAccess.Database.Base
{
    using Platform.Data;

    public interface IDatabase
    {
        IDatabaseSetting GetDatabaseSetting();

        IDbCollection<T> GetCollection<T>() where T : IEntity;

        void DropCollection<T>() where T : IEntity;

        void DropCollection(string name);

        void RenameCollection(string oldName, string newName);
    }
}
