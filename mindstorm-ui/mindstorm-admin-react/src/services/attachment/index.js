import request from '@/utils/request';

export async function uploadQuestionImage(image) {
  return request('/mindstorm-file/file/question-attachment/upload/image', {
    method: 'POST',
    data: image,
  });
}
