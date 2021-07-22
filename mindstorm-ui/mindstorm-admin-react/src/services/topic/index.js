import request from '@/utils/request';

export async function listTopics() {
  return request('/mindstorm-education/education/topic/list/select');
}

export async function createTopic(topicVO) {
  return request('/mindstorm-education/education/topic/create', {
    method: 'POST',
    data: topicVO,
  });
}

export async function updateQuestionType(topicVO) {
  return request('/mindstorm-education/education/topic/update', {
    method: 'POST',
    data: topicVO,
  });
}

export async function deleteQuestionType(topicId) {
  return request(`/mindstorm-education/education/topic/delete/${topicId}`, {
    method: 'DELETE',
  });
}
