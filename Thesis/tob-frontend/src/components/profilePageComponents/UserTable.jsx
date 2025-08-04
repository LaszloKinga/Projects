import React from "react";
import styles from "../../styles/ProfileCard.module.css";
import { formatDate } from "../../utils/formatDate";

const UserTable = ({ users, onDelete, onEdit }) => {
  return (
    <div className={`${styles.tableContainer} container`}>
      <table className={`${styles.table} table table-striped`}>
        <thead>
          <tr>
            <th>#</th>
            <th>First name</th>
            <th>Last name</th>
            <th>User name</th>
            <th>Email</th>
            <th>Create date</th>
            <th>Update date</th>
            <th>Update</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.firstName}</td>
              <td>{user.lastName}</td>
              <td>{user.userName}</td>
              <td>{user.email}</td>
              <td>{formatDate(user.createdAt)}</td>
              <td>{user.updatedAt ? formatDate(user.updatedAt) : "Not Updated"}</td>
              <td>
                <button className="btn btn-warning" onClick={() => onEdit(user)}>
                  Update
                </button>
              </td>
              <td>
                <button className="btn btn-danger ml-2" onClick={() => onDelete(user.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UserTable;
