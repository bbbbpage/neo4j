/*
 * Copyright (c) 2002-2020 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.procs

import org.neo4j.cypher.internal.ast.AccessDatabaseAction
import org.neo4j.cypher.internal.ast.AdminAction
import org.neo4j.cypher.internal.ast.AllAdminAction
import org.neo4j.cypher.internal.ast.AllDatabaseAction
import org.neo4j.cypher.internal.ast.AllRoleActions
import org.neo4j.cypher.internal.ast.AllUserActions
import org.neo4j.cypher.internal.ast.AlterUserAction
import org.neo4j.cypher.internal.ast.AssignRoleAction
import org.neo4j.cypher.internal.ast.ConstraintManagementAction
import org.neo4j.cypher.internal.ast.CreateConstraintAction
import org.neo4j.cypher.internal.ast.CreateDatabaseAction
import org.neo4j.cypher.internal.ast.CreateIndexAction
import org.neo4j.cypher.internal.ast.CreateNodeLabelAction
import org.neo4j.cypher.internal.ast.CreatePropertyKeyAction
import org.neo4j.cypher.internal.ast.CreateRelationshipTypeAction
import org.neo4j.cypher.internal.ast.CreateRoleAction
import org.neo4j.cypher.internal.ast.CreateUserAction
import org.neo4j.cypher.internal.ast.DenyPrivilegeAction
import org.neo4j.cypher.internal.ast.DropConstraintAction
import org.neo4j.cypher.internal.ast.DropDatabaseAction
import org.neo4j.cypher.internal.ast.DropIndexAction
import org.neo4j.cypher.internal.ast.DropRoleAction
import org.neo4j.cypher.internal.ast.DropUserAction
import org.neo4j.cypher.internal.ast.GrantPrivilegeAction
import org.neo4j.cypher.internal.ast.IndexManagementAction
import org.neo4j.cypher.internal.ast.RemoveRoleAction
import org.neo4j.cypher.internal.ast.RevokePrivilegeAction
import org.neo4j.cypher.internal.ast.SchemaManagementAction
import org.neo4j.cypher.internal.ast.ShowPrivilegeAction
import org.neo4j.cypher.internal.ast.ShowRoleAction
import org.neo4j.cypher.internal.ast.ShowUserAction
import org.neo4j.cypher.internal.ast.StartDatabaseAction
import org.neo4j.cypher.internal.ast.StopDatabaseAction
import org.neo4j.cypher.internal.ast.TokenManagementAction
import org.neo4j.internal.kernel.api.security

object AdminActionMapper {
  def asKernelAction(action: AdminAction): security.PrivilegeAction = action match {
    case AccessDatabaseAction => security.PrivilegeAction.ACCESS

    case StartDatabaseAction => security.PrivilegeAction.START_DATABASE
    case StopDatabaseAction => security.PrivilegeAction.STOP_DATABASE
    case CreateDatabaseAction => security.PrivilegeAction.CREATE_DATABASE
    case DropDatabaseAction => security.PrivilegeAction.DROP_DATABASE

    case IndexManagementAction => security.PrivilegeAction.INDEX
    case CreateIndexAction => security.PrivilegeAction.CREATE_INDEX
    case DropIndexAction => security.PrivilegeAction.DROP_INDEX
    case ConstraintManagementAction => security.PrivilegeAction.CONSTRAINT
    case SchemaManagementAction => security.PrivilegeAction.SCHEMA
    case CreateConstraintAction => security.PrivilegeAction.CREATE_CONSTRAINT
    case DropConstraintAction => security.PrivilegeAction.DROP_CONSTRAINT

    case TokenManagementAction => security.PrivilegeAction.TOKEN
    case CreateNodeLabelAction => security.PrivilegeAction.CREATE_LABEL
    case CreateRelationshipTypeAction => security.PrivilegeAction.CREATE_RELTYPE
    case CreatePropertyKeyAction => security.PrivilegeAction.CREATE_PROPERTYKEY

    case ShowUserAction => security.PrivilegeAction.SHOW_USER
    case CreateUserAction => security.PrivilegeAction.CREATE_USER
    case AlterUserAction => security.PrivilegeAction.ALTER_USER
    case DropUserAction => security.PrivilegeAction.DROP_USER

    case AllRoleActions => security.PrivilegeAction.ROLE_MANAGEMENT
    case ShowRoleAction => security.PrivilegeAction.SHOW_ROLE
    case CreateRoleAction => security.PrivilegeAction.CREATE_ROLE
    case DropRoleAction => security.PrivilegeAction.DROP_ROLE
    case AssignRoleAction => security.PrivilegeAction.ASSIGN_ROLE
    case RemoveRoleAction => security.PrivilegeAction.REMOVE_ROLE

    case AllUserActions => security.PrivilegeAction.USER_MANAGEMENT
    case ShowPrivilegeAction => security.PrivilegeAction.SHOW_PRIVILEGE
    case GrantPrivilegeAction => security.PrivilegeAction.GRANT_PRIVILEGE
    case RevokePrivilegeAction => security.PrivilegeAction.REVOKE_PRIVILEGE
    case DenyPrivilegeAction => security.PrivilegeAction.DENY_PRIVILEGE

    case AllDatabaseAction => security.PrivilegeAction.DATABASE_ACTIONS

    case AllAdminAction => security.PrivilegeAction.ADMIN
  }

  def asCypherAdminAction(action: security.PrivilegeAction): AdminAction = action match {
    case security.PrivilegeAction.START_DATABASE => StartDatabaseAction
    case security.PrivilegeAction.STOP_DATABASE => StopDatabaseAction
    case security.PrivilegeAction.CREATE_DATABASE => CreateDatabaseAction
    case security.PrivilegeAction.DROP_DATABASE => DropDatabaseAction

    case security.PrivilegeAction.INDEX => IndexManagementAction
    case security.PrivilegeAction.CREATE_INDEX => CreateIndexAction
    case security.PrivilegeAction.DROP_INDEX => DropIndexAction
    case security.PrivilegeAction.CONSTRAINT => ConstraintManagementAction
    case security.PrivilegeAction.SCHEMA => SchemaManagementAction
    case security.PrivilegeAction.CREATE_CONSTRAINT => CreateConstraintAction
    case security.PrivilegeAction.DROP_CONSTRAINT => DropConstraintAction

    case security.PrivilegeAction.TOKEN => TokenManagementAction
    case security.PrivilegeAction.CREATE_LABEL => CreateNodeLabelAction
    case security.PrivilegeAction.CREATE_RELTYPE => CreateRelationshipTypeAction
    case security.PrivilegeAction.CREATE_PROPERTYKEY => CreatePropertyKeyAction

    case security.PrivilegeAction.SHOW_USER => ShowUserAction
    case security.PrivilegeAction.CREATE_USER => CreateUserAction
    case security.PrivilegeAction.ALTER_USER => AlterUserAction
    case security.PrivilegeAction.DROP_USER => DropUserAction

    case security.PrivilegeAction.ROLE_MANAGEMENT => AllRoleActions
    case security.PrivilegeAction.SHOW_ROLE => ShowRoleAction
    case security.PrivilegeAction.CREATE_ROLE => CreateRoleAction
    case security.PrivilegeAction.DROP_ROLE => DropRoleAction
    case security.PrivilegeAction.ASSIGN_ROLE => AssignRoleAction
    case security.PrivilegeAction.REMOVE_ROLE => RemoveRoleAction

    case security.PrivilegeAction.USER_MANAGEMENT => AllUserActions
    case security.PrivilegeAction.SHOW_PRIVILEGE => ShowPrivilegeAction
    case security.PrivilegeAction.GRANT_PRIVILEGE => GrantPrivilegeAction
    case security.PrivilegeAction.REVOKE_PRIVILEGE => RevokePrivilegeAction
    case security.PrivilegeAction.DENY_PRIVILEGE => DenyPrivilegeAction

    case security.PrivilegeAction.DATABASE_ACTIONS => AllDatabaseAction

    case security.PrivilegeAction.ADMIN => AllAdminAction
  }
}