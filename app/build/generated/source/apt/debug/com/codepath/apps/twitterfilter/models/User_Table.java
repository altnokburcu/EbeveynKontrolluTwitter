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
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class User_Table extends ModelAdapter<User> {
  public static final Property<String> name = new Property<String>(User.class, "name");

  /**
   * Primary Key */
  public static final Property<Long> uid = new Property<Long>(User.class, "uid");

  public static final Property<String> screenName = new Property<String>(User.class, "screenName");

  public static final Property<String> profileImageUrl = new Property<String>(User.class, "profileImageUrl");

  public static final Property<String> tagline = new Property<String>(User.class, "tagline");

  public static final Property<Integer> followingCount = new Property<Integer>(User.class, "followingCount");

  public static final Property<Integer> followersCount = new Property<Integer>(User.class, "followersCount");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{name,uid,screenName,profileImageUrl,tagline,followingCount,followersCount};

  public User_Table(DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
  }

  @Override
  public final Class<User> getModelClass() {
    return User.class;
  }

  @Override
  public final String getTableName() {
    return "`User`";
  }

  @Override
  public final User newInstance() {
    return new User();
  }

  @Override
  public final Property getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch ((columnName)) {
      case "`name`":  {
        return name;
      }
      case "`uid`":  {
        return uid;
      }
      case "`screenName`":  {
        return screenName;
      }
      case "`profileImageUrl`":  {
        return profileImageUrl;
      }
      case "`tagline`":  {
        return tagline;
      }
      case "`followingCount`":  {
        return followingCount;
      }
      case "`followersCount`":  {
        return followersCount;
      }
      default: {
        throw new IllegalArgumentException("Invalid column name passed. Ensure you are calling the correct table's column");
      }
    }
  }

  @Override
  public final IProperty[] getAllColumnProperties() {
    return ALL_COLUMN_PROPERTIES;
  }

  @Override
  public final void bindToInsertValues(ContentValues values, User model) {
    values.put("`name`", model.name != null ? model.name : null);
    values.put("`uid`", model.uid);
    values.put("`screenName`", model.screenName != null ? model.screenName : null);
    values.put("`profileImageUrl`", model.profileImageUrl != null ? model.profileImageUrl : null);
    values.put("`tagline`", model.tagline != null ? model.tagline : null);
    values.put("`followingCount`", model.followingCount);
    values.put("`followersCount`", model.followersCount);
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, User model, int start) {
    statement.bindStringOrNull(1 + start, model.name);
    statement.bindLong(2 + start, model.uid);
    statement.bindStringOrNull(3 + start, model.screenName);
    statement.bindStringOrNull(4 + start, model.profileImageUrl);
    statement.bindStringOrNull(5 + start, model.tagline);
    statement.bindLong(6 + start, model.followingCount);
    statement.bindLong(7 + start, model.followersCount);
  }

  @Override
  public final void bindToUpdateStatement(DatabaseStatement statement, User model) {
    statement.bindStringOrNull(1, model.name);
    statement.bindLong(2, model.uid);
    statement.bindStringOrNull(3, model.screenName);
    statement.bindStringOrNull(4, model.profileImageUrl);
    statement.bindStringOrNull(5, model.tagline);
    statement.bindLong(6, model.followingCount);
    statement.bindLong(7, model.followersCount);
    statement.bindLong(8, model.uid);
  }

  @Override
  public final void bindToDeleteStatement(DatabaseStatement statement, User model) {
    statement.bindLong(1, model.uid);
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `User`(`name`,`uid`,`screenName`,`profileImageUrl`,`tagline`,`followingCount`,`followersCount`) VALUES (?,?,?,?,?,?,?)";
  }

  @Override
  public final String getUpdateStatementQuery() {
    return "UPDATE `User` SET `name`=?,`uid`=?,`screenName`=?,`profileImageUrl`=?,`tagline`=?,`followingCount`=?,`followersCount`=? WHERE `uid`=?";
  }

  @Override
  public final String getDeleteStatementQuery() {
    return "DELETE FROM `User` WHERE `uid`=?";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `User`(`name` TEXT, `uid` INTEGER, `screenName` TEXT, `profileImageUrl` TEXT, `tagline` TEXT, `followingCount` INTEGER, `followersCount` INTEGER, PRIMARY KEY(`uid`))";
  }

  @Override
  public final void loadFromCursor(FlowCursor cursor, User model) {
    model.name = cursor.getStringOrDefault("name");
    model.uid = cursor.getLongOrDefault("uid");
    model.screenName = cursor.getStringOrDefault("screenName");
    model.profileImageUrl = cursor.getStringOrDefault("profileImageUrl");
    model.tagline = cursor.getStringOrDefault("tagline");
    model.followingCount = cursor.getIntOrDefault("followingCount");
    model.followersCount = cursor.getIntOrDefault("followersCount");
  }

  @Override
  public final boolean exists(User model, DatabaseWrapper wrapper) {
    return SQLite.selectCountOf()
    .from(User.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final OperatorGroup getPrimaryConditionClause(User model) {
    OperatorGroup clause = OperatorGroup.clause();
    clause.and(uid.eq(model.uid));
    return clause;
  }
}
