export const checkForNull = (req, res, next) => {
  if (req.headers['content-type'] && req.headers['content-type'].startsWith('multipart/form-data')) {
    next();
  } else {
    if (req.method === 'POST') {
      const body = req.body || {};
      if (!req.body || Object.keys(body).length === 0) {
        return res.status(400).json({ error: 'Request body cannot be empty.' });
      }

      // Exit the function if there are any null or undefined values
      if ([...Object.values(req.body)].some((value) => value === null || value === undefined || value === '')) {
        return res.status(400).json({
          error: 'One or more values must not be null, undefined or empty.',
        });
      }
    }
    // No null or undefined values found, proceed to the next middleware
    next();
  }
};
