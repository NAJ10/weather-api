package com.gamma.db

import anorm.SQL
import com.gamma.Issue
import javax.sql.DataSource

class AnormIssueRepository(ds: DataSource) extends IssueRepository {
  def createIssue(issue: Issue) : Unit = {
    withConnection(ds) { implicit connection =>
      SQL("INSERT INTO Issue (name, email, description) VALUES ({name}, {email}, {description})")
        .on("name" -> issue.name, "email" -> issue.email, "description" -> issue.description).executeInsert()
    }
  }
}
