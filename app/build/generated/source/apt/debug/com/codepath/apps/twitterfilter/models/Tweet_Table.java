package com.codepath.apps.twitterfilter.models;

import android.content.ContentValues;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sql.QueryBuilder;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.IProperty;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseStatement;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.FlowCursor;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class Tweet_Table extends ModelAdapter<Tweet> {
  public static final Property<String> body = new Property<String>(Tweet.class, "body");

  /**
   * Primary Key */
  public static final Property<Long> uid = new Property<Long>(Tweet.class, "uid");

  public static final Property<String> createdAt = new Property<String>(Tweet.class, "createdAt");

  /**
   * Foreign Key */
  public static final Property<Long> user_uid = new Property<Long>(Tweet.class, "user_uid");

  public static final Property<String> mediaUrl = new Property<String>(Tweet.class, "mediaUrl");

  public static final Property<String> url = new Property<String>(Tweet.class, "url");

  public static final Property<Boolean> retweeted = new Property<Boolean>(Tweet.class, "retweeted");

  public static final Property<Boolean> favorited = new Property<Boolean>(Tweet.class, "favorited");

  public static final Property<Integer> retweet_count = new Property<Integer>(Tweet.class, "retweet_count");

  public static final Property<Integer> favorite_count = new Property<Integer>(Tweet.class, "favorite_count");

  public static final Property<String> location = new Property<String>(Tweet.class, "location");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{body,uid,createdAt,user_uid,mediaUrl,url,retweeted,favorited,retweet_count,favorite_count,location};

  public Tweet_Table(DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
  }

  @Override
  public final Class<Tweet> getModelClass() {
    return Tweet.class;
  }

  @Override
  public final String getTableName() {
    return "`Tweet`";
  }

  @Override
  public final Tweet newInstance() {
    return new Tweet();
  }

  @Override
  public final Property getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch ((columnName)) {
      case "`body`":  {
        return body;
      }
      case "`uid`":  {
        return uid;
      }
      case "`createdAt`":  {
        return createdAt;
      }
      case "`user_uid`": {
        return user_uid;
      }
      case "`mediaUrl`":  {
        return mediaUrl;
      }
      case "`url`":  {
        return url;
      }
      case "`retweeted`":  {
        return retweeted;
      }
      case "`favorited`":  {
        return favorited;
      }
      case "`retweet_count`":  {
        return retweet_count;
      }
      case "`favorite_count`":  {
        return favorite_count;
      }
      case "`location`":  {
        return location;
      }
      default: {
        throw new IllegalArgumentException("Invalid column name passed. Ensure you are calling the correct table's column");
      }
    }
  }

  @Override
  public final void saveForeignKeys(Tweet model, DatabaseWrapper wrapper) {
    if (model.user != null) {
      model.user.save(wrapper);
    }
  }

  @Override
  public final IProperty[] getAllColumnProperties() {
    return ALL_COLUMN_PROPERTIES;
  }

  @Override
  public final void bindToInsertValues(ContentValues values, Tweet model) {
    values.put("`body`", model.body != null ? model.body : null);
    values.put("`uid`", model.uid);
    values.put("`createdAt`", model.createdAt != null ? model.createdAt : null);
    if (model.user != null) {
      values.put("`user_uid`", model.user.uid);
    } else {
      values.putNull("`user_uid`");
    }
    values.put("`mediaUrl`", model.mediaUrl != null ? model.mediaUrl : null);
    values.put("`url`", model.url != null ? model.url : null);
    values.put("`retweeted`", model.retweeted ? 1 : 0);
    values.put("`favorited`", model.favorited ? 1 : 0);
    values.put("`retweet_count`", model.retweet_count);
    values.put("`favorite_count`", model.favorite_count);
    values.put("`location`", model.location != null ? model.location : null);
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, Tweet model, int start) {
    statement.bindStringOrNull(1 + start, model.body);
    statement.bindLong(2 + start, model.uid);
    statement.bindStringOrNull(3 + start, model.createdAt);
    if (model.user != null) {
      statement.bindLong(4 + start, model.user.uid);
    } else {
      statement.bindNull(4 + start);
    }
    statement.bindStringOrNull(5 + start, model.mediaUrl);
    statement.bindStringOrNull(6 + start, model.url);
    statement.bindLong(7 + start, model.retweeted ? 1 : 0);
    statement.bindLong(8 + start, model.favorited ? 1 : 0);
    statement.bindLong(9 + start, model.retweet_count);
    statement.bindLong(10 + start, model.favorite_count);
    statement.bindStringOrNull(11 + start, model.location);
  }

  @Override
  public final void bindToUpdateStatement(DatabaseStatement statement, Tweet model) {
    statement.bindStringOrNull(1, model.body);
    statement.bindLong(2, model.uid);
    statement.bindStringOrNull(3, model.createdAt);
    if (model.user != null) {
      statement.bindLong(4, model.user.uid);
    } else {
      statement.bindNull(4);
    }
    statement.bindStringOrNull(5, model.mediaUrl);
    statement.bindStringOrNull(6, model.url);
    statement.bindLong(7, model.retweeted ? 1 : 0);
    statement.bindLong(8, model.favorited ? 1 : 0);
    statement.bindLong(9, model.retweet_count);
    statement.bindLong(10, model.favorite_count);
    statement.bindStringOrNull(11, model.location);
    statement.bindLong(12, model.uid);
  }

  @Override
  public final void bindToDeleteStatement(DatabaseStatement statement, Tweet model) {
    statement.bindLong(1, model.uid);
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `Tweet`(`body`,`uid`,`createdAt`,`user_uid`,`mediaUrl`,`url`,`retweeted`,`favorited`,`retweet_count`,`favorite_count`,`location`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getUpdateStatementQuery() {
    return "UPDATE `Tweet` SET `body`=?,`uid`=?,`createdAt`=?,`user_uid`=?,`mediaUrl`=?,`url`=?,`retweeted`=?,`favorited`=?,`retweet_count`=?,`favorite_count`=?,`location`=? WHERE `uid`=?";
  }

  @Override
  public final String getDeleteStatementQuery() {
    return "DELETE FROM `Tweet` WHERE `uid`=?";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `Tweet`(`body` TEXT, `uid` INTEGER, `createdAt` TEXT, `user_uid` INTEGER, `mediaUrl` TEXT, `url` TEXT, `retweeted` INTEGER, `favorited` INTEGER, `retweet_count` INTEGER, `favorite_count` INTEGER, `location` TEXT, PRIMARY KEY(`uid`)"+ ", FOREIGN KEY(`user_uid`) REFERENCES " + com.raizlabs.android.dbflow.config.FlowManager.getTableName(com.codepath.apps.twitterfilter.models.User.class) + "(`uid`) ON UPDATE NO ACTION ON DELETE NO ACTION" + ");";
  }

  @Override
  public final void loadFromCursor(FlowCursor cursor, Tweet model) {
    model.body = cursor.getStringOrDefault("body");
    model.uid = cursor.getLongOrDefault("uid");
    model.createdAt = cursor.getStringOrDefault("createdAt");
    int index_user_uid_User_Table = cursor.getColumnIndex("user_uid");
    if (index_user_uid_User_Table != -1 && !cursor.isNull(index_user_uid_User_Table)) {
      model.user = SQLite.select().from(User.class).where()
          .and(User_Table.uid.eq(cursor.getLong(index_user_uid_User_Table)))
          .querySingle();
    } else {
      model.user = null;
    }
    model.mediaUrl = cursor.getStringOrDefault("mediaUrl");
    model.url = cursor.getStringOrDefault("url");
    int index_retweeted = cursor.getColumnIndex("retweeted");
    if (index_retweeted != -1 && !cursor.isNull(index_retweeted)) {
      model.retweeted = cursor.getBoolean(index_retweeted);
    } else {
      model.retweeted = false;
    }
    int index_favorited = cursor.getColumnIndex("favorited");
    if (index_favorited != -1 && !cursor.isNull(index_favorited)) {
      model.favorited = cursor.getBoolean(index_favorited);
    } else {
      model.favorited = false;
    }
    model.retweet_count = cursor.getIntOrDefault("retweet_count");
    model.favorite_count = cursor.getIntOrDefault("favorite_count");
    model.location = cursor.getStringOrDefault("location");
  }

  @Override
  public final boolean exists(Tweet model, DatabaseWrapper wrapper) {
    return SQLite.selectCountOf()
    .from(Tweet.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final OperatorGroup getPrimaryConditionClause(Tweet model) {
    OperatorGroup clause = OperatorGroup.clause();
    clause.and(uid.eq(model.uid));
    return clause;
  }
}
