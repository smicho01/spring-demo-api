# SQS conf na AWS

Policy na SQS:

`{
  "Version": "2012-10-17",
  "Id": "__default_policy_ID",
  "Statement": [
    {
      "Sid": "__owner_statement",
      "Effect": "Allow",
      "Principal": {
        "AWS": "arn roota [powinien byc juz z automatu]"
      },
      "Action": "SQS:*",
      "Resource": "tutaj arn Quueue"
    },
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "sns.amazonaws.com"
      },
      "Action": "sqs:SendMessage",
      "Resource": "tutaj arn Quueue",
      "Condition": {
        "ArnEquals": {
          "aws:SourceArn": "tutaj arn Topic .fifo"
        }
      }
    }
  ]
}`