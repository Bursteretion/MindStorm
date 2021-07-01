import React from 'react'
import { Redirect } from 'umi'
import { getToken } from '@/utils/authority'

export default (props) => {
  const isLogin = !!getToken()
  if (isLogin) {
    return <div>{ props.children }</div>
  }
  return <Redirect to="/user/login" />
}
