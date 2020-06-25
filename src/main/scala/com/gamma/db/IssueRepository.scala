package com.gamma.db

import com.gamma.Issue

trait IssueRepository {
  def createIssue(issue: Issue) : Unit
}
