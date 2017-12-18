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
import java.lang.Long;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class SampleModel_Table extends ModelAdapter<SampleModel> {
  /**
   * Primary Key */
  public static final Property<Long> id = new Property<Long>(SampleModel.class, "id");

  public static final Property<String> name = new Property<String>(SampleModel.class, "name");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{id,name};

  public SampleModel_Table(DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
  }

  @Override
  public final Class<SampleModel> getModelClass() {
    return SampleModel.class;
  }

  @Override
  public final String getTableName() {
    return "`SampleModel`";
  }

  @Override
  public final SampleModel newInstance() {
    return new SampleModel();
  }

  @Override
  public final Property getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch ((columnName)) {
      case "`id`":  {
        return id;
      }
      case "`name`":  {
        return name;
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
  public final void bindToInsertValues(ContentValues values, SampleModel model) {
    values.put("`id`", model.id != null ? model.id : null);
    values.put("`name`", model.getName() != null ? model.getName() : null);
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, SampleModel model,
      int start) {
    statement.bindNumberOrNull(1 + start, model.id);
    statement.bindStringOrNull(2 + start, model.getName());
  }

  @Override
  public final void bindToUpdateStatement(DatabaseStatement statement, SampleModel model) {
    statement.bindNumberOrNull(1, model.id);
    statement.bindStringOrNull(2, model.getName());
    statement.bindNumberOrNull(3, model.id);
  }

  @Override
  public final void bindToDeleteStatement(DatabaseStatement statement, SampleModel model) {
    statement.bindNumberOrNull(1, model.id);
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `SampleModel`(`id`,`name`) VALUES (?,?)";
  }

  @Override
  public final String getUpdateStatementQuery() {
    return "UPDATE `SampleModel` SET `id`=?,`name`=? WHERE `id`=?";
  }

  @Override
  public final String getDeleteStatementQuery() {
    return "DELETE FROM `SampleModel` WHERE `id`=?";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `SampleModel`(`id` INTEGER, `name` TEXT, PRIMARY KEY(`id`))";
  }

  @Override
  public final void loadFromCursor(FlowCursor cursor, SampleModel model) {
    model.id = cursor.getLongOrDefault("id");
    model.setName(cursor.getStringOrDefault("name"));
  }

  @Override
  public final boolean exists(SampleModel model, DatabaseWrapper wrapper) {
    return SQLite.selectCountOf()
    .from(SampleModel.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final OperatorGroup getPrimaryConditionClause(SampleModel model) {
    OperatorGroup clause = OperatorGroup.clause();
    clause.and(id.eq(model.id));
    return clause;
  }
}
